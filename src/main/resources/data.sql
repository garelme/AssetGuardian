-- ==========================================
-- 1. KATEGORİLER (Categories)
-- ==========================================
INSERT INTO category (id, name, description, created_at, is_deleted) VALUES
                                                                           (1, 'Laptop', 'Yüksek performanslı dizüstü bilgisayarlar', NOW(), false),
                                                                           (2, 'Phone', 'Şirket hattı tanımlı akıllı telefonlar', NOW(), false),
                                                                           (3, 'Monitor', 'Harici ekranlar ve monitörler', NOW(), false),
                                                                           (4, 'License', 'Yazılım lisansları (JetBrains, Office vb.)', NOW(), false);

-- ==========================================
-- 2. KULLANICILAR (Users)
-- Şifrelerin hepsi: 123456
-- ==========================================
INSERT INTO users (id,name,username, email, password, role, department, created_at, is_deleted) VALUES
-- Admin Kullanıcısı (IT Departmanı)
(1,'Mehmet Akif Alan' ,'admin', 'admin@sirket.com', '$2a$10$R/h5X.5r.t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/', 'ADMIN', 'IT', NOW(), false),

-- Yönetici (Manager)
(2,'Hamdi Tanpınar' ,'mudur_bey', 'mudur@sirket.com', '$2a$10$R/h5X.5r.t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/', 'MANAGER', 'MANAGEMENT', NOW(), false),

-- Standart Çalışanlar (Personel)
(3,'Ahmet Yılmaz', 'ahmet_yilmaz', 'ahmet@sirket.com', '$2a$10$R/h5X.5r.t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/', 'USER', 'IT', NOW(), false),
(4,'Ayşe demir', 'ayse_demir', 'ayse@sirket.com', '$2a$10$R/h5X.5r.t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/', 'USER', 'HR', NOW(), false),
(5, 'Mehmet Öz','mehmet_oz', 'mehmet@sirket.com', '$2a$10$R/h5X.5r.t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/t5/', 'USER', 'SALES', NOW(), false);

-- ==========================================
-- 3. DEMİRBAŞLAR (Assets)
-- Status: 0=IN_STOCK, 1=ASSIGNED, 2=BROKEN, 3=SCRAPPED (Enum sırasına göre)
-- ==========================================

-- Laptoplar (Category ID: 1)
INSERT INTO asset (id, name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
                                                                                                      (1, 'MacBook Pro M2 16"', 'MB-PRO-2023-001', 75000.00, 'IN_STOCK', 1, NOW(), false),
                                                                                                      (2, 'MacBook Air M1', 'MB-AIR-2021-992', 35000.00, 'ASSIGNED', 1, NOW(), false), -- Ahmet'te
                                                                                                      (3, 'Dell XPS 15', 'DELL-XPS-5500', 45000.00, 'ASSIGNED', 1, NOW(), false),    -- Ayşe'de
                                                                                                      (4, 'Lenovo ThinkPad X1', 'LNV-TP-X1-004', 50000.00, 'BROKEN', 1, NOW(), false); -- Bozuk

-- Telefonlar (Category ID: 2)
INSERT INTO asset (id, name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
                                                                                                      (5, 'iPhone 14 Pro', 'IPH-14-PRO-111', 60000.00, 'IN_STOCK', 2, NOW(), false),
                                                                                                      (6, 'Samsung S23 Ultra', 'SMS-S23-ULT-222', 55000.00, 'ASSIGNED', 2, NOW(), false); -- Mehmet'te

-- Monitörler (Category ID: 3)
INSERT INTO asset (id, name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
                                                                                                      (7, 'Dell 27" 4K Monitor', 'DLL-MON-4K-01', 12000.00, 'IN_STOCK', 3, NOW(), false),
                                                                                                      (8, 'LG UltraWide 34"', 'LG-UW-34-02', 15000.00, 'IN_STOCK', 3, NOW(), false);

-- Lisanslar (Category ID: 4)
INSERT INTO asset (id, name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
    (9, 'IntelliJ IDEA Ultimate', 'JB-IDEA-LIC-01', 5000.00, 'ASSIGNED', 4, NOW(), false); -- Ahmet'te

-- ==========================================
-- 4. ZİMMETLER (Assignments) - (Opsiyonel)
-- Kimde ne var?
-- ==========================================
INSERT INTO assignment (id, asset_id, user_id,assigned_by, assigned_date, status, created_at, is_deleted) VALUES
                                                                                                    (1, 2, 3,1, NOW(), 'ACTIVE', NOW(), false),  -- MacBook Air -> Ahmet
                                                                                                    (2, 9, 3,1,NOW(), 'ACTIVE', NOW(), false),  -- IntelliJ Lisans -> Ahmet
                                                                                                    (3, 3, 4,1, NOW(), 'ACTIVE', NOW(), false),  -- Dell XPS -> Ayşe
                                                                                                    (4, 6, 5,1, NOW(), 'ACTIVE', NOW(), false);  -- Samsung -> Mehmet
INSERT INTO demand (id,user_id,category_id,description,created_at,status,urgency,is_deleted) VALUES
(1,3,1,'Yeni bir laptop ihtiyacım var.',NOW(),'PENDING','HIGH',false),
(2,4,2,'Şirket telefonu talep ediyorum.',NOW(),'APPROVED','MEDIUM',false);