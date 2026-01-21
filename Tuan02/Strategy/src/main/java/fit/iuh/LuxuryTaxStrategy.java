package fit.iuh;

public class LuxuryTaxStrategy implements TaxStrategy {
    public double calculate(double price) { return price * 0.3; } // 30% Thuế xa xỉ
}
