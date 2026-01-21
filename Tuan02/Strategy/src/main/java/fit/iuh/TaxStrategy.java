package fit.iuh;

// 1. Interface cho chiến lược tính thuế
public interface TaxStrategy {
    double calculate(double price);
}