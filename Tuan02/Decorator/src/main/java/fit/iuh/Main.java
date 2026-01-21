package fit.iuh;

public class Main {
    public static void main(String[] args) {
        // Thanh toán cơ bản 100$
        Payment payment = new BasicPayment();

        // Thêm phí xử lý
        payment = new ProcessingFeeDecorator(payment);

        // Thêm mã giảm giá
        payment = new DiscountDecorator(payment);

        System.out.println("Tổng số tiền phải thanh toán cuối cùng: " + payment.getCost(100.0));
    }
}