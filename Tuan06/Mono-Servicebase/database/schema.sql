-- =============================================
-- Online Food Delivery Database Schema (MariaDB)
-- =============================================

-- Tạo database
CREATE DATABASE IF NOT EXISTS food_delivery;
USE food_delivery;

-- =============================================
-- 1. USERS TABLE - Quản lý người dùng
-- =============================================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    role ENUM('CUSTOMER', 'RESTAURANT', 'ADMIN') DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 2. MENU_ITEMS TABLE - Quản lý món ăn
-- =============================================
CREATE TABLE menu_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50),
    image_url VARCHAR(255),
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- 3. ORDERS TABLE - Quản lý đơn hàng
-- =============================================
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'PREPARING', 'DELIVERING', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    delivery_address VARCHAR(255),
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- =============================================
-- 4. ORDER_ITEMS TABLE - Chi tiết đơn hàng
-- =============================================
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);

-- =============================================
-- Sample Data
-- =============================================

-- Users
INSERT INTO users (username, password, email, full_name, phone, address, role) VALUES
('admin', '123456', 'admin@food.com', 'Admin User', '0901234567', 'HCM City', 'ADMIN'),
('customer1', '123456', 'customer1@food.com', 'Nguyen Van A', '0901111111', '123 Le Loi, Q1', 'CUSTOMER'),
('customer2', '123456', 'customer2@food.com', 'Tran Thi B', '0902222222', '456 Nguyen Hue, Q1', 'CUSTOMER');

-- Menu Items
INSERT INTO menu_items (name, description, price, category, available) VALUES
('Phở Bò', 'Phở bò truyền thống Hà Nội', 45000, 'Món chính', TRUE),
('Bún Chả', 'Bún chả Hà Nội đặc biệt', 50000, 'Món chính', TRUE),
('Cơm Tấm', 'Cơm tấm sườn bì chả', 55000, 'Món chính', TRUE),
('Bánh Mì', 'Bánh mì thịt đặc biệt', 25000, 'Ăn vặt', TRUE),
('Trà Đá', 'Trà đá mát lạnh', 5000, 'Đồ uống', TRUE),
('Nước Cam', 'Nước cam tươi', 20000, 'Đồ uống', TRUE);

-- Sample Order
INSERT INTO orders (user_id, total_amount, status, delivery_address, note) VALUES
(2, 95000, 'COMPLETED', '123 Le Loi, Q1', 'Giao nhanh');

INSERT INTO order_items (order_id, menu_item_id, quantity, price) VALUES
(1, 1, 1, 45000),
(1, 2, 1, 50000);
