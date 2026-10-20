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
                                   ('Bone Conduction')
ON CONFLICT (name) DO NOTHING;

-- EARPHONES
INSERT INTO earphone (brand_id, model, msrp) VALUES
                                              ((SELECT id FROM brand WHERE name='Moondrop'), 'Blessing 3', 319.99),
                                              ((SELECT id FROM brand WHERE name='7Hz'), 'Timeless', 219.00),
                                              ((SELECT id FROM brand WHERE name='Sennheiser'), 'IE 600', 699.95),
                                              ((SELECT id FROM brand WHERE name='Sony'), 'IER-Z1R', 1699.99),
                                              ((SELECT id FROM brand WHERE name='Kiwi Ears'), 'Quartet', 109.00)
ON CONFLICT (brand_id, model) DO NOTHING;

-- EARPHONE DRIVERS
INSERT INTO driver (earphone_id, driver_type_id, quantity)
SELECT e.id, dt.id, v.quantity
FROM (VALUES
          ('Moondrop','Blessing 3','Dynamic Driver',2),
          ('Moondrop','Blessing 3','Balanced Armature',4),
          ('7Hz','Timeless','Planar Magnetic',1),
          ('Sennheiser','IE 600','Dynamic Driver',1),
          ('Sony','IER-Z1R','Dynamic Driver',2),
          ('Sony','IER-Z1R','Balanced Armature',1),
          ('Kiwi Ears','Quartet','Dynamic Driver',2),
          ('Kiwi Ears','Quartet','Balanced Armature',2)
     ) AS v(brand, model, driver_type, quantity)
         JOIN brand b        ON b.name = v.brand
         JOIN earphone e     ON e.brand_id = b.id AND e.model = v.model
         JOIN driver_type dt ON dt.name = v.driver_type
ON CONFLICT (earphone_id, driver_type_id) DO NOTHING;

INSERT INTO headphone (brand_id, model, msrp) VALUES
                                                 ((SELECT id FROM brand WHERE name='Moondrop'), 'Venus', 599.99),
                                                 ((SELECT id FROM brand WHERE name='Sennheiser'), 'Hd800s', 1799.95),
                                                 ((SELECT id FROM brand WHERE name='Sony'), 'MDR-Z1R', 2099.99),
                                                 ((SELECT id FROM brand WHERE name='Kiwi Ears'), 'Quartet', 69.99)

ON CONFLICT (brand_id, model) DO NOTHING;

INSERT INTO driver (headphone_id, driver_type_id, quantity)
SELECT h.id, dt.id, v.quantity
FROM (VALUES
        ('Moondrop','Venus','Planar Magnetic',1),
        ('Sennheiser','Hd800s','Dynamic Driver',1),
        ('Sony','MDR-Z1R','Dynamic Driver',1),
        ('Kiwi Ears','Quartet','Dynamic Driver',1)
    ) AS v(brand, model, driver_type, quantity)
        JOIN brand b        ON b.name = v.brand
        JOIN headphone h    ON h.brand_id = b.id AND h.model = v.model
        JOIN driver_type dt ON dt.name = v.driver_type
ON CONFLICT (headphone_id, driver_type_id) DO NOTHING;