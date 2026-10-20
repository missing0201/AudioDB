package com.pm.playerservice.service;

import com.pm.playerservice.dto.request.*;
import com.pm.playerservice.dto.response.PlayerResponse;
import com.pm.playerservice.exception.*;
import com.pm.playerservice.mapper.AmpMapper;
import com.pm.playerservice.mapper.ComboMapper;
import com.pm.playerservice.mapper.DacMapper;
import com.pm.playerservice.model.Brand;
import com.pm.playerservice.model.Player;
import com.pm.playerservice.model.PlayerType;
import com.pm.playerservice.model.amp.AmpSpec;
import com.pm.playerservice.model.dac.DacChip;
import com.pm.playerservice.model.dac.DacSpec;
import com.pm.playerservice.repository.BrandRepo;
import com.pm.playerservice.repository.DacChipModelRepo;
import com.pm.playerservice.repository.PlayerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepo playerRepo;
    private final BrandRepo brandRepo;
    private final DacChipModelRepo chipModelRepo;

    public PlayerService(PlayerRepo playerRepo, BrandRepo brandRepo, DacChipModelRepo chipModelRepo) {
        this.playerRepo = playerRepo;
        this.brandRepo = brandRepo;
        this.chipModelRepo = chipModelRepo;
    }

    @Transactional(readOnly = true)
    public List<PlayerResponse> getPlayers(PlayerType type) {

        List<Player> players = type == null
                ? playerRepo.findAll()
                : playerRepo.findByPlayerType(type);

        return players.stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlayerResponse getPlayer(Long id) {
        Player requestedPlayer=playerRepo.findById(id).orElseThrow(()->new PlayerNotFoundException("Player not found"));
        return toDTO(requestedPlayer);
    }

    @Transactional
    public PlayerResponse addPlayer(PlayerRequest req) {
        Brand brand = resolveBrand(req.brand());
        if (playerRepo.existsByBrandAndModelIgnoreCase(brand, req.model()))
            throw new PlayerAlreadyExistsException(brand.getName() + " " + req.model());

        Player player = new Player();                 // no client id, so save() does persist, not merge
        player.setBrand(brand);
        player.setModel(req.model());
        applySpecs(player, req);
        return toDTO(playerRepo.save(player));        // use the returned entity: it has the generated id
    }

    @Transactional
    public PlayerResponse updatePlayer(Long id, PlayerRequest req) {
        Player player = playerRepo.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        if (typeOf(req) != player.getPlayerType())
            throw new InvalidPlayerRequestException("Cannot change player type from "
                    + player.getPlayerType() + " to " + typeOf(req));
        Brand brand = resolveBrand(req.brand());
        if (playerRepo.existsByBrandAndModelIgnoreCaseAndIdNot(brand, req.model(), id))
            throw new PlayerAlreadyExistsException(brand.getName() + " " + req.model());

        player.setBrand(brand);
        player.setModel(req.model());
        applySpecs(player, req);                      // mutates existing spec objects in place
        return toDTO(player);
    }

    @Transactional
    public void deletePlayer(Long id) {
        Player p = playerRepo.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        playerRepo.delete(p);
    }

    private Brand resolveBrand(String name) {
        return brandRepo.findByNameIgnoreCase(name).orElseThrow(() -> new BrandNotFoundException(name));
    }

    private PlayerType typeOf(PlayerRequest req) {
        return switch (req) {
            case DacRequestDTO r -> PlayerType.DAC;
            case AmpRequestDTO r -> PlayerType.AMP;
            case ComboRequestDTO r -> PlayerType.DAC_AMP;
        };
    }

    private void applySpecs(Player p, PlayerRequest req) {
        switch (req) {
            case DacRequestDTO r   -> applyDac(p, r.dacSpec());
            case AmpRequestDTO r   -> applyAmp(p, r.ampSpec());
            case ComboRequestDTO r -> { applyDac(p, r.dacSpec()); applyAmp(p, r.ampSpec()); }
        }
    }

    private void applyDac(Player p, DacSpecRequest r) {
        DacSpec spec = p.getDacSpec();
        if (spec == null) { spec = new DacSpec(); p.setDacSpec(spec); }   // setter links spec.player
        spec.setBitDepth(r.bitDepth());
        spec.setOverSampling(r.overSampling());

        Map<Long, DacChip> existing = spec.getDacChips().stream()
                .collect(Collectors.toMap(c -> c.getModel().getId(), c -> c));
        Set<Long> incoming = new HashSet<>();
        for (ChipRequest cr : r.chips()) {
            if (!incoming.add(cr.chipModelId()))
                throw new InvalidPlayerRequestException("Duplicate chip model: " + cr.chipModelId());
            DacChip chip = existing.get(cr.chipModelId());
            if (chip == null) {
                chip = new DacChip();
                chip.setDacSpec(spec);                                    // the missing back-reference
                chip.setModel(chipModelRepo.findById(cr.chipModelId())
                        .orElseThrow(() -> new ChipModelNotFoundException(cr.chipModelId())));
                spec.getDacChips().add(chip);
            }
            chip.setQuantity(cr.quantity());
        }
        spec.getDacChips().removeIf(c -> !incoming.contains(c.getModel().getId()));
    }

    private void applyAmp(Player p, AmpSpecRequest r) {
        if (r.minImpedance() > r.maxImpedance())
            throw new InvalidPlayerRequestException("minImpedance must be <= maxImpedance");
        AmpSpec spec = p.getAmpSpec();
        if (spec == null) { spec = new AmpSpec(); p.setAmpSpec(spec); }
        spec.setPowerOutput(r.powerOutput());
        spec.setMinImpedance(r.minImpedance());
        spec.setMaxImpedance(r.maxImpedance());
        spec.setAmpClass(r.ampClass());
    }

    private PlayerResponse toDTO(Player player) {
        return switch (player.getPlayerType()) {
            case DAC -> DacMapper.toDTO(player);
            case AMP -> AmpMapper.toDTO(player);
            case DAC_AMP -> ComboMapper.toDTO(player);
        };
    }
}
