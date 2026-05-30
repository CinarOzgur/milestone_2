-- schema.sql
-- Relational Table 1 Master Dimension Table for Geographic Entities
CREATE TABLE IF NOT EXISTS locations (
    zone_id INT PRIMARY KEY,
    lga_name VARCHAR(100) NOT NULL,
    region_type VARCHAR(50) NOT NULL
);

-- Relational Table 2 Transactional Fact Table for Environment Metrics
CREATE TABLE IF NOT EXISTS accident_records (
    accident_id INT AUTO_INCREMENT PRIMARY KEY,
    zone_id INT,
    light_condition VARCHAR(50) NOT NULL,
    road_surface VARCHAR(50) NOT NULL,
    atmospheric_condition VARCHAR(50) NOT NULL,
    youth_serious_injury_count INT DEFAULT 0,
    fatal_count INT DEFAULT 0,
    FOREIGN KEY (zone_id) REFERENCES locations(zone_id)
);

-- data.sql
-- Population script loading realistic test matrices mapping to your project baseline metrics
INSERT INTO locations (zone_id, lga_name, region_type) VALUES 
(101, 'Southeast Melbourne', 'Metropolitan'),
(102, 'Greater Geelong', 'Regional'),
(103, 'Yarra Ranges', 'Regional'),
(104, 'Mornington Peninsula', 'Metropolitan');

INSERT INTO accident_records (zone_id, light_condition, road_surface, atmospheric_condition, youth_serious_injury_count, fatal_count) VALUES 
(101, 'Night (Street lights off)', 'Wet', 'Raining', 1, 0),
(101, 'Day', 'Dry', 'Clear', 0, 0),
(102, 'Day', 'Dry', 'Clear', 2, 1),
(102, 'Night (Street lights off)', 'Wet', 'Raining', 4, 1),
(103, 'Night (Street lights off)', 'Wet', 'Raining', 5, 2),
(103, 'Night (Street lights off)', 'Wet', 'Raining', 3, 1),
(104, 'Day', 'Dry', 'Clear', 0, 0);