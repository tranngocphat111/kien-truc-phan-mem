# Project: Payment System Add-ons (Decorator Pattern)

## 1. Mô tả bài toán
Bạn có một phương thức thanh toán cơ bản. Tuy nhiên, khách hàng có thể áp dụng thêm nhiều tính năng phụ phí hoặc ưu đãi như: **Phí xử lý giao dịch**, **Mã giảm giá**, hoặc **Phí vận chuyển**.

Nếu dùng kế thừa, bạn sẽ rơi vào tình trạng "bùng nổ lớp con" (ví dụ: `PaymentWithDiscount`, `PaymentWithFee`, `PaymentWithDiscountAndFee`...). Điều này cực kỳ khó quản lý.

## 2. Giải pháp: Decorator Design Pattern
Decorator Pattern cho phép bạn thêm các trách nhiệm mới vào một đối tượng một cách động bằng cách đặt đối tượng đó vào bên trong các đối tượng "bao bọc" (wrapper).



### Các thành phần trong Code:
* **Payment (Component):** Giao diện chung cho cả đối tượng gốc và các bộ trang trí.
* **BasicPayment (Concrete Component):** Đối tượng gốc cần được thêm tính năng.
* **PaymentDecorator (Base Decorator):** Lớp trung gian giữ tham chiếu đến đối tượng `Payment`.
* **DiscountDecorator, ProcessingFeeDecorator (Concrete Decorators):** Thêm logic giảm giá hoặc cộng phí vào kết quả của đối tượng bên trong.

## 3. Kết luận
- **Ưu điểm:** Linh hoạt hơn kế thừa rất nhiều. Bạn có thể kết hợp các lớp trang trí theo bất kỳ thứ tự nào (ví dụ: Giảm giá xong mới cộng phí, hoặc ngược lại).
- **Khi nào dùng:** Khi bạn muốn mở rộng chức năng của đối tượng một cách linh động tại thời điểm chạy mà không làm ảnh hưởng đến các đối tượng khác cùng lớp.