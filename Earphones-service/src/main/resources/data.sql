-- DRIVER TYPES
INSERT INTO driver_type (name) VALUES
                                   ('Dynamic Driver'),
                                   ('Balanced Armature'),
                                   ('Planar Magnetic'),
                                   ('Electrostatic'),
                                   ('Bone Conduction');

-- EARPHONES
INSERT INTO earphone (brand, model, msrp) VALUES
                                              ('Moondrop', 'Blessing 3', 319.99),
                                              ('7Hz', 'Timeless', 219.00),
                                              ('Sennheiser', 'IE 600', 699.95),
                                              ('Sony', 'IER-Z1R', 1699.99),
                                              ('Kiwi Ears', 'Quartet', 109.00);

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