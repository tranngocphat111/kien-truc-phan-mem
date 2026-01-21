package fit.iuh;

public class NewState implements OrderState{
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: MỚI TẠO. Đang kiểm tra thông tin đơn hàng...");
        context.setState(new ProcessingState()); // Chuyển sang trạng thái tiếp theo
    }
}
