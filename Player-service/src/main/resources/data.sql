-- src/main/resources/data.sql  (PostgreSQL)
-- One INSERT per table; safe to re-run thanks to ON CONFLICT DO NOTHING
-- (requires the unique constraints on brand, dac_chip_model, player, dac_chip).
-- Spec values are illustrative sample data, not verified manufacturer figures.

-- brands
INSERT INTO brand (name, origin) VALUES
                                     ('Topping', 'China'),
                                     ('SMSL', 'China'),
                                     ('FiiO', 'China'),
                                     ('iFi Audio', 'United Kingdom'),
                                     ('Schiit', 'United States'),
                                     ('ESS Technology', 'United States'),
                                     ('AKM', 'Japan'),
                                     ('Texas Instruments', 'United States')
ON CONFLICT DO NOTHING;

-- dac chip models
INSERT INTO dac_chip_model (brand_id, model)
SELECT b.id, v.model
FROM (VALUES
          ('ESS Technology', 'ES9038Q2M'),
          ('ESS Technology', 'ES9068AS'),
          ('AKM', 'AK4493SEQ'),
          ('AKM', 'AK4499EX'),
          ('Texas Instruments', 'PCM5102A')
     ) AS v(brand, model)
         JOIN brand b ON b.name = v.brand
ON CONFLICT DO NOTHING;

-- players (player_type set explicitly: @PrePersist does not run for raw SQL)
INSERT INTO player (brand_id, model, player_type)
SELECT b.id, v.model, v.player_type
FROM (VALUES
          ('Topping',   'D90SE',        'DAC'),
          ('Topping',   'A90 Discrete', 'AMP'),
          ('SMSL',      'SU-9',         'DAC'),
          ('FiiO',      'K11',          'DAC_AMP'),
          ('FiiO',      'K5 Pro',       'DAC_AMP'),
          ('iFi Audio', 'Zen DAC V2',   'DAC_AMP'),
          ('Schiit',    'Magni',        'AMP')
     ) AS v(brand, model, player_type)
         JOIN brand b ON b.name = v.brand
ON CONFLICT DO NOTHING;

-- dac_spec (PK = player_id)
INSERT INTO dac_spec (player_id, bit_depth, over_sampling)
SELECT p.id, v.bit_depth, v.over_sampling
FROM (VALUES
          ('Topping',   'D90SE',      32, TRUE),
          ('SMSL',      'SU-9',       32, TRUE),
          ('FiiO',      'K11',        32, TRUE),
          ('FiiO',      'K5 Pro',     32, TRUE),
          ('iFi Audio', 'Zen DAC V2', 32, FALSE)
     ) AS v(brand, model, bit_depth, over_sampling)
         JOIN brand b  ON b.name = v.brand
         JOIN player p ON p.brand_id = b.id AND p.model = v.model
ON CONFLICT DO NOTHING;

-- dac_chip
INSERT INTO dac_chip (dac_spec_id, model_id, quantity)
SELECT p.id, m.id, v.quantity
FROM (VALUES
          ('Topping',   'D90SE',      'AKM',               'AK4499EX',  2),
          ('SMSL',      'SU-9',       'ESS Technology',    'ES9068AS',  1),
          ('FiiO',      'K11',        'AKM',               'AK4493SEQ', 1),
          ('FiiO',      'K5 Pro',     'ESS Technology',    'ES9038Q2M', 1),
          ('iFi Audio', 'Zen DAC V2', 'Texas Instruments', 'PCM5102A',  1)
     ) AS v(player_brand, player_model, chip_brand, chip_model, quantity)
         JOIN brand pb          ON pb.name = v.player_brand
         JOIN player p          ON p.brand_id = pb.id AND p.model = v.player_model
         JOIN brand cb          ON cb.name = v.chip_brand
         JOIN dac_chip_model m  ON m.brand_id = cb.id AND m.model = v.chip_model
ON CONFLICT DO NOTHING;

-- amp_spec (PK = player_id)
INSERT INTO amp_spec (player_id, power_output, min_impedance, max_impedance, amp_class)
SELECT p.id, v.power_output, v.min_impedance, v.max_impedance, v.amp_class
FROM (VALUES
          ('Topping',   'A90 Discrete', 6.0, 16, 600, 'AB'),
          ('FiiO',      'K11',          1.3, 16, 300, 'AB'),
          ('FiiO',      'K5 Pro',       2.0, 16, 300, 'AB'),
          ('iFi Audio', 'Zen DAC V2',   0.5, 16, 600, 'AB'),
          ('Schiit',    'Magni',        1.2, 16, 300, 'AB')
     ) AS v(brand, model, power_output, min_impedance, max_impedance, amp_class)
         JOIN brand b  ON b.name = v.brand
         JOIN player p ON p.brand_id = b.id AND p.model = v.model
ON CONFLICT DO NOTHING;