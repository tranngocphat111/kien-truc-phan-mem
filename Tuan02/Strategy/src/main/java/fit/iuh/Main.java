package fit.iuh;

// 4. Kiểm thử
public class Main {
    public static void main(String[] args) {
        // Một chiếc xe hơi với thuế xa xỉ
        Product car = new Product("BMW", 100000, new LuxuryTaxStrategy());
        System.out.println("Giá xe sau thuế: " + car.getTotalPrice());

        // Đổi sang thuế VAT (ví dụ khi chính sách nhà nước thay đổi)
        car.setTaxStrategy(new VATStrategy());
        System.out.println("Giá xe nếu chỉ tính VAT: " + car.getTotalPrice());
    }
}