package fit.iuh;

public class DeliveredState implements OrderState{
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: ĐÃ GIAO. Cập nhật hệ thống: Hoàn tất đơn hàng.");
    }
}
