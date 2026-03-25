# Database Partition Demo

Demo 3 loại Database Partition với MariaDB + Spring Boot + React

## Kiến trúc

```
┌─────────────────────────────────────────────────────────────────┐
│                        FRONTEND (React)                         │
│                    http://localhost:5173                         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND (Spring Boot)                         │
│                    http://localhost:8080                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────────┐  │
│  │ Horizontal  │  │  Vertical   │  │        Function         │  │
│  │ Controller  │  │ Controller  │  │       Controller        │  │
│  └─────────────┘  └─────────────┘  └─────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      MariaDB Database                            │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │ HORIZONTAL PARTITION (Chia theo ROW)                       │  │
│  │ ┌─────────────┐        ┌──────────────┐                   │  │
│  │ │ user_male   │        │ user_female  │                   │  │
│  │ │ (Nam)       │        │ (Nữ)         │                   │  │
│  │ └─────────────┘        └──────────────┘                   │  │
│  └───────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │ VERTICAL PARTITION (Chia theo COLUMN)                      │  │
│  │ ┌─────────────┐        ┌──────────────┐                   │  │
│  │ │ user_basic  │───────▶│ user_detail  │                   │  │
│  │ │ (Cơ bản)    │        │ (Chi tiết)   │                   │  │
│  │ └─────────────┘        └──────────────┘                   │  │
│  └───────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │ FUNCTION PARTITION (Chia theo CHỨC NĂNG)                   │  │
│  │ ┌─────────────┐        ┌──────────────┐                   │  │
│  │ │ user_order  │        │ user_log     │                   │  │
│  │ │ (Đơn hàng)  │        │ (Log)        │                   │  │
│  │ └─────────────┘        └──────────────┘                   │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## 3 Loại Partition

### 1. Horizontal Partition (Row-based)
- **Nguyên lý**: Chia dữ liệu theo hàng dựa trên điều kiện
- **Ví dụ**: Chia user theo giới tính
  - `user_male` - Chứa tất cả user nam
  - `user_female` - Chứa tất cả user nữ
- **Ưu điểm**:
  - Query nhanh hơn khi chỉ cần 1 partition
  - Dễ scale từng partition riêng biệt
  - Giảm kích thước bảng

### 2. Vertical Partition (Column-based)
- **Nguyên lý**: Chia dữ liệu theo cột
- **Ví dụ**: Tách thông tin user
  - `user_basic` - Thông tin cơ bản (id, name, email) - truy cập thường xuyên
  - `user_detail` - Thông tin chi tiết (address, phone, bio) - ít truy cập
- **Ưu điểm**:
  - Query nhanh khi chỉ cần thông tin cơ bản
  - Giảm I/O vì không load toàn bộ dữ liệu
  - Dễ cache thông tin thường dùng

### 3. Function Partition
- **Nguyên lý**: Chia dữ liệu theo chức năng nghiệp vụ
- **Ví dụ**: Tách theo chức năng
  - `user_order` - Quản lý đơn hàng
  - `user_log` - Ghi log hoạt động
- **Ưu điểm**:
  - Mỗi bảng phục vụ một chức năng riêng
  - Dễ bảo trì và mở rộng
  - Có thể đặt trên các server khác nhau

## Hướng dẫn chạy

### 1. Chuẩn bị Database
```bash
# Mở MariaDB và chạy script
mysql -u root -p < database.sql
```

Hoặc copy nội dung file `database.sql` và chạy trong MySQL Workbench/phpMyAdmin.

### 2. Chạy Backend
```bash
cd backend
./mvnw spring-boot:run
```
Backend sẽ chạy tại: http://localhost:8080

### 3. Chạy Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend sẽ chạy tại: http://localhost:5173

## API Endpoints

### Horizontal Partition
- `POST /api/horizontal/users` - Tạo user (tự động chọn bảng theo giới tính)
- `GET /api/horizontal/users` - Lấy tất cả users
- `GET /api/horizontal/users/male` - Lấy users nam
- `GET /api/horizontal/users/female` - Lấy users nữ
- `DELETE /api/horizontal/users/{gender}/{id}` - Xóa user

### Vertical Partition
- `POST /api/vertical/users` - Tạo user (lưu vào 2 bảng)
- `GET /api/vertical/users/basic` - Lấy thông tin cơ bản (query nhanh)
- `GET /api/vertical/users/full` - Lấy thông tin đầy đủ (join 2 bảng)
- `GET /api/vertical/users/{id}/full` - Lấy full info của 1 user
- `DELETE /api/vertical/users/{id}` - Xóa user

### Function Partition
- `POST /api/function/orders` - Tạo đơn hàng
- `GET /api/function/orders` - Lấy tất cả đơn hàng
- `PUT /api/function/orders/{id}/status` - Cập nhật trạng thái
- `POST /api/function/logs` - Ghi log
- `GET /api/function/logs` - Lấy tất cả logs

## Công nghệ sử dụng
- **Backend**: Spring Boot 3.2, Spring Data JPA, Lombok
- **Frontend**: React 18, Vite, Axios
- **Database**: MariaDB

---
IUH - Kiến trúc phần mềm - Tuần 06
