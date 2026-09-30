INSERT INTO brand (name, origin) VALUES
                                     ('Moondrop','China'), ('7Hz','China'), ('Sennheiser','Germany'),
                                     ('Sony','Japan'), ('Kiwi Ears','China')
    ON CONFLICT (name) DO NOTHING;

-- DRIVER TYPES
INSERT INTO driver_type (name) VALUES
                                   ('Dynamic Driver'),
                                   ('Balanced Armature'),
                                   ('Planar Magnetic'),
                                   ('Electrostatic'),
                                   ('Bone Conduction');

-- EARPHONES
INSERT INTO earphone (brand_id, model, msrp) VALUES
                                              ((SELECT id FROM brand WHERE name='Moondrop'), 'Blessing 3', 319.99),
                                              ((SELECT id FROM brand WHERE name='7Hz'), 'Timeless', 219.00),
                                              ((SELECT id FROM brand WHERE name='Sennheiser'), 'IE 600', 699.95),
                                              ((SELECT id FROM brand WHERE name='Sony'), 'IER-Z1R', 1699.99),
                                              ((SELECT id FROM brand WHERE name='Kiwi Ears'), 'Quartet', 109.00)
ON CONFLICT (brand_id, model) DO NOTHING;

-- EARPHONE DRIVERS
INSERT INTO earphone_driver (earphone_id, driver_type_id, quantity) VALUES
                                                                        (1, 1, 2),
                                                                        (1, 2, 4),

                                                                        (2, 3, 1),

                                                                        (3, 1, 1),

                                                                        (4, 1, 2),
                                                                        (4, 2, 1),

                                                                        (5, 1, 2),
                                                                        (5, 2, 2);