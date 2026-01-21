# Design Pattern: Factory Method
**Tình huống áp dụng:** Hệ thống gửi thông báo đa kênh (`Email`, `SMS`, `Push`).

## 1. Chuyện gì xảy ra nếu KHÔNG áp dụng Factory Method?
Nếu không sử dụng Factory, logic khởi tạo đối tượng sẽ bị trộn lẫn vào logic nghiệp vụ của hệ thống.

* **Vi phạm nguyên tắc Open-Closed:** Mỗi khi bạn muốn thêm một loại thông báo mới (ví dụ: gửi qua Zalo hoặc Telegram), bạn phải tìm đến tất cả những nơi đang xử lý thông báo để sửa code `if-else`.
* **Code "Spaghetti" (Mỳ tôm):** Các đoạn mã kiểm tra điều kiện để tạo đối tượng sẽ lặp đi lặp lại ở nhiều file khác nhau, cực kỳ khó bảo trì.
* **Phụ thuộc chặt chẽ (Tight Coupling):** Lớp xử lý chính phải biết quá chi tiết về các lớp con (như cách tạo Email, cách tạo SMS), làm mất đi tính linh hoạt của hướng đối tượng.

## 2. Ví dụ về Code "Xấu" (Anti-pattern)
Khi không có Factory, bạn phải dùng `if-else` thủ công khắp nơi:

```java
public void processNotification(String type, String message) {
    if (type.equals("email")) {
        EmailNotification email = new EmailNotification();
        email.send(message);
    } else if (type.equals("sms")) {
        SmsNotification sms = new SmsNotification();
        sms.send(message);
    }
    // Muốn thêm Zalo? Bạn buộc phải vào đây sửa code và có nguy cơ làm hỏng code cũ.
}