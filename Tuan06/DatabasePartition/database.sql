-- =============================================
-- DATABASE PARTITION DEMO - SQL Script
-- MariaDB
-- =============================================

-- Tạo database
CREATE DATABASE IF NOT EXISTS partition_demo;
USE partition_demo;

-- =============================================
-- 1. HORIZONTAL PARTITION (Chia theo ROW)
-- Chia dữ liệu theo giới tính
-- =============================================

-- Bảng user nam
CREATE TABLE IF NOT EXISTS user_male (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INT,
    gender VARCHAR(10) DEFAULT 'MALE'
);

-- Bảng user nữ
CREATE TABLE IF NOT EXISTS user_female (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INT,
    gender VARCHAR(10) DEFAULT 'FEMALE'
);

-- =============================================
-- 2. VERTICAL PARTITION (Chia theo COLUMN)
-- Chia dữ liệu theo tần suất truy cập
-- =============================================

-- Bảng thông tin cơ bản (truy cập thường xuyên)
CREATE TABLE IF NOT EXISTS user_basic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    gender VARCHAR(10)
);

-- Bảng thông tin chi tiết (ít truy cập)
CREATE TABLE IF NOT EXISTS user_detail (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20),
    bio TEXT,
    age INT,
    FOREIGN KEY (user_id) REFERENCES user_basic(id) ON DELETE CASCADE
);

-- =============================================
-- 3. FUNCTION PARTITION (Chia theo CHỨC NĂNG)
-- Mỗi bảng phục vụ một chức năng riêng
-- =============================================

-- Bảng đơn hàng
CREATE TABLE IF NOT EXISTS user_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    amount DOUBLE,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING'
);

-- Bảng log hoạt động
CREATE TABLE IF NOT EXISTS user_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    description TEXT,
    log_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(50)
);

-- =============================================
-- INSERT DỮ LIỆU MẪU
-- =============================================

-- Horizontal Partition - Users
INSERT INTO user_male (name, email, age) VALUES
('Nguyễn Văn A', 'a@gmail.com', 25),
('Trần Văn B', 'b@gmail.com', 30),
('Lê Văn C', 'c@gmail.com', 28);

INSERT INTO user_female (name, email, age) VALUES
('Nguyễn Thị X', 'x@gmail.com', 22),
('Trần Thị Y', 'y@gmail.com', 26),
('Lê Thị Z', 'z@gmail.com', 24);

-- Vertical Partition - Users
INSERT INTO user_basic (name, email, gender) VALUES
('Phạm Minh D', 'd@gmail.com', 'MALE'),
('Hoàng Thị E', 'e@gmail.com', 'FEMALE');

INSERT INTO user_detail (user_id, address, phone, bio, age) VALUES
(1, '123 Nguyễn Trãi, Q1, HCM', '0901234567', 'Developer tại công ty ABC', 27),
(2, '456 Lê Lợi, Q3, HCM', '0909876543', 'Designer tại công ty XYZ', 25);

-- Function Partition - Orders & Logs
INSERT INTO user_order (user_id, product_name, amount, status) VALUES
(1, 'iPhone 15', 25000000, 'PENDING'),
(1, 'MacBook Pro', 50000000, 'CONFIRMED'),
(2, 'iPad Air', 15000000, 'SHIPPED');

INSERT INTO user_log (user_id, action, description, ip_address) VALUES
(1, 'LOGIN', 'User đăng nhập thành công', '192.168.1.100'),
(1, 'VIEW', 'Xem sản phẩm iPhone 15', '192.168.1.100'),
(2, 'PURCHASE', 'Mua iPad Air', '192.168.1.101');
