# Project: Product Tax Calculation (Strategy Pattern)

## 1. Mô tả bài toán
Trong hệ thống bán hàng, mỗi loại sản phẩm hoặc mỗi khu vực địa lý lại có một công thức tính thuế khác nhau (VAT 10%, Thuế xa xỉ 30%, hoặc Miễn thuế).

Nếu cài đặt trực tiếp logic tính thuế vào lớp `Product`, mỗi khi chính sách thuế thay đổi, bạn phải sửa lại mã nguồn của lớp `Product`, điều này vi phạm nguyên tắc đóng gói.

## 2. Giải pháp: Strategy Design Pattern
Strategy Pattern định nghĩa một tập hợp các thuật toán (chiến lược tính thuế), đóng gói từng thuật toán lại và làm cho chúng có thể thay đổi linh hoạt cho nhau.



[Image of Strategy Design Pattern diagram]


### Các thành phần trong Code:
* **TaxStrategy (Interface):** Khai báo phương thức `calculate()`.
* **VATStrategy, LuxuryTaxStrategy (Concrete Strategies):** Các thuật toán tính thuế cụ thể.
* **Product (Context):** Chứa một tham chiếu đến `TaxStrategy`. Bạn có thể thay đổi chiến lược tính thuế của sản phẩm ngay tại thời điểm thực thi (Runtime).

## 3. Kết luận
- **Ưu điểm:** Tách biệt mã nguồn xử lý nghiệp vụ (Product) khỏi mã nguồn tính toán (Tax). Dễ dàng thêm các loại thuế mới mà không cần chỉnh sửa code cũ.
- **Khi nào dùng:** Khi bạn có nhiều cách (thuật toán) khác nhau để thực hiện một công việc và muốn chọn lựa chúng một cách linh động.