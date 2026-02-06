-- ==========================================
-- 1. KATEGORİLER (Categories)
-- ==========================================
INSERT INTO category (name, description, created_at, is_deleted) VALUES
('Laptop', 'Yüksek performanslı dizüstü bilgisayarlar', NOW(), false),
('Phone', 'Şirket hattı tanımlı akıllı telefonlar', NOW(), false),
('Monitor', 'Harici ekranlar ve monitörler', NOW(), false),
('License', 'Yazılım lisansları (JetBrains, Office vb.)', NOW(), false);

-- ==========================================
-- 2. KULLANICILAR (Users)
-- Şifrelerin hepsi: 123456
-- ==========================================
INSERT INTO users (name,username, email, password, role, department, created_at, is_deleted, profile_image_path) VALUES
-- Admin Kullanıcısı (IT Departmanı)
('Mehmet Akif Alan' ,'admin', 'admin@sirket.com', '$2a$12$Vkrh1HAw4C6vmKPcXLUDOeOugEy12O/k//HZt2WSYBLTRs4o7Xqh6', 'ADMIN', 'IT', NOW(), false, 'default-avatar.png'),

-- Yönetici (Manager)
('Hamdi Tanpınar' ,'mudur_bey', 'mudur@sirket.com', '$2a$12$Vkrh1HAw4C6vmKPcXLUDOeOugEy12O/k//HZt2WSYBLTRs4o7Xqh6', 'MANAGER', 'MANAGEMENT', NOW(), false, 'default-avatar.png'),

-- Standart Çalışanlar (Personel)
('Ahmet Yılmaz', 'ahmet_yilmaz', 'ahmet@sirket.com', '$2a$12$Vkrh1HAw4C6vmKPcXLUDOeOugEy12O/k//HZt2WSYBLTRs4o7Xqh6', 'USER', 'IT', NOW(), false, 'default-avatar.png'),
('Ayşe demir', 'ayse_demir', 'ayse@sirket.com', '$2a$12$Vkrh1HAw4C6vmKPcXLUDOeOugEy12O/k//HZt2WSYBLTRs4o7Xqh6', 'USER', 'HR', NOW(), false, 'default-avatar.png'),
( 'Mehmet Öz','mehmet_oz', 'mehmet@sirket.com', '$2a$12$Vkrh1HAw4C6vmKPcXLUDOeOugEy12O/k//HZt2WSYBLTRs4o7Xqh6', 'USER', 'SALES', NOW(), false, 'default-avatar.png');

-- ==========================================
-- 3. DEMİRBAŞLAR (Assets)
-- Status: 0=IN_STOCK, 1=ASSIGNED, 2=BROKEN, 3=SCRAPPED (Enum sırasına göre)
-- ==========================================

-- Laptoplar (Category ID: 1)
INSERT INTO asset (name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
('MacBook Pro M2 16"', 'MB-PRO-2023-001', 75000.00, 'IN_STOCK', 1, NOW(), false),
('MacBook Air M1', 'MB-AIR-2021-992', 35000.00, 'ASSIGNED', 1, NOW(), false), -- Ahmet'te
('Dell XPS 15', 'DELL-XPS-5500', 45000.00, 'ASSIGNED', 1, NOW(), false),    -- Ayşe'de
('Lenovo ThinkPad X1', 'LNV-TP-X1-004', 50000.00, 'BROKEN', 1, NOW(), false); -- Bozuk

-- Telefonlar (Category ID: 2)
INSERT INTO asset (name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
('iPhone 14 Pro', 'IPH-14-PRO-111', 60000.00, 'IN_STOCK', 2, NOW(), false),
('Samsung S23 Ultra', 'SMS-S23-ULT-222', 55000.00, 'ASSIGNED', 2, NOW(), false); -- Mehmet'te

-- Monitörler (Category ID: 3)
INSERT INTO asset (name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
('Dell 27" 4K Monitor', 'DLL-MON-4K-01', 12000.00, 'IN_STOCK', 3, NOW(), false),
('LG UltraWide 34"', 'LG-UW-34-02', 15000.00, 'IN_STOCK', 3, NOW(), false);

-- Lisanslar (Category ID: 4)
INSERT INTO asset (name, serial_number, price, status, category_id, created_at, is_deleted) VALUES
('IntelliJ IDEA Ultimate', 'JB-IDEA-LIC-01', 5000.00, 'ASSIGNED', 4, NOW(), false); -- Ahmet'te

-- ==========================================
-- 4. ZİMMETLER (Assignments) - (Opsiyonel)
-- Kimde ne var?
-- ==========================================
INSERT INTO assignment (asset_id, user_id,assigned_by, assigned_date, status, created_at, is_deleted) VALUES
(2, 3,1, NOW(), 'ACTIVE', NOW(), false),  -- MacBook Air -> Ahmet
(9, 3,1,NOW(), 'ACTIVE', NOW(), false),  -- IntelliJ Lisans -> Ahmet
(3, 4,1, NOW(), 'ACTIVE', NOW(), false),  -- Dell XPS -> Ayşe
(6, 5,1, NOW(), 'ACTIVE', NOW(), false);  -- Samsung -> Mehmet

INSERT INTO demand (user_id,category_id,description,created_at,status,urgency,is_deleted) VALUES
(3,1,'Yeni bir laptop ihtiyacım var.',NOW(),'PENDING','HIGH',false),
(4,2,'Şirket telefonu talep ediyorum.',NOW(),'APPROVED','MEDIUM',false);