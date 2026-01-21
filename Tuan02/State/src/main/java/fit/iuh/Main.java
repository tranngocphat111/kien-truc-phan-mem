package fit.iuh;

public class Main {
    public static void main(String[] args) {
        OrderContext order = new OrderContext();

        order.apply(); // Mới tạo -> Đang xử lý
        order.apply(); // Đang xử lý -> Đã giao
        order.apply(); // Đã giao
    }
}