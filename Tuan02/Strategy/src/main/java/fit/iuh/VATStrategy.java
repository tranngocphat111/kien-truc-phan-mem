package fit.iuh;

public class VATStrategy implements TaxStrategy {
    public double calculate(double price) { return price * 0.1; } // 10% VAT
}
