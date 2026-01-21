package fit.iuh;

public class NoTaxStrategy implements TaxStrategy {
    public double calculate(double price) { return 0; }
}
