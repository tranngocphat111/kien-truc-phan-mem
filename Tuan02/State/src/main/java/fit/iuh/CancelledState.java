package fit.iuh;

public class CancelledState implements OrderState{
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: HỦY. Đang thực hiện hoàn tiền cho khách hàng...");
    }
}
