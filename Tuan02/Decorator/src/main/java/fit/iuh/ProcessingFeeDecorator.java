package fit.iuh;

public class ProcessingFeeDecorator extends PaymentDecorator {
    public ProcessingFeeDecorator(Payment payment) { super(payment); }

    public double getCost(double amount) {
        return super.getCost(amount) + 5.0; // Cộng thêm 5$ phí xử lý
    }
}
