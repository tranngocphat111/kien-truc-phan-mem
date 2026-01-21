package fit.iuh;

public class DiscountDecorator extends PaymentDecorator {
    public DiscountDecorator(Payment payment) { super(payment); }

    public double getCost(double amount) {
        return super.getCost(amount) - 10.0; // Giảm giá 10$
    }
}
