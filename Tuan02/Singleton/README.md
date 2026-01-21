# Design Pattern: Singleton
**Tình huống áp dụng:** Quản lý thực thể kết nối Cơ sở dữ liệu (`DatabaseConnector`).

## 1. Chuyện gì xảy ra nếu KHÔNG áp dụng Singleton?
Nếu không sử dụng Singleton, mỗi khi một thành phần trong hệ thống (như Quản lý đơn hàng, Quản lý kho, Quản lý người dùng) cần truy vấn dữ liệu, chúng sẽ tạo ra một đối tượng kết nối mới.

* **Lãng phí tài nguyên:** Việc khởi tạo kết nối Database rất "đắt đỏ" về CPU và RAM. Tạo quá nhiều đối tượng giống hệt nhau sẽ làm chậm hệ thống.
* **Lỗi "Too Many Connections":** Mỗi Database (MySQL, PostgreSQL) đều có giới hạn số lượng kết nối cùng lúc. Nếu mỗi User hoặc mỗi hàm đều `new DatabaseConnector()`, hệ thống sẽ sớm bị sập vì vượt quá giới hạn này.
* **Khó kiểm soát:** Không có một nơi duy nhất để quản lý trạng thái kết nối hoặc thực hiện cấu hình chung.

## 2. Ví dụ về Code "Xấu" (Anti-pattern)
Khi không có Singleton, code bị phân tán và lãng phí:

```java
// Ở module Đơn hàng
public void saveOrder() {
    DatabaseConnector db = new DatabaseConnector(); // Tạo mới instance 1
    db.execute("INSERT...");
}

// Ở module Sản phẩm
public void getProducts() {
    DatabaseConnector db = new DatabaseConnector(); // Lại tạo mới instance 2
    db.execute("SELECT...");
}