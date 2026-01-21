package fit.iuh;

public abstract class PaymentDecorator implements Payment {
    protected Payment decoratedPayment;

    public PaymentDecorator(Payment payment) {
        this.decoratedPayment = payment;
    }

    public double getCost(double amount) {
        return decoratedPayment.getCost(amount);
    }
}
