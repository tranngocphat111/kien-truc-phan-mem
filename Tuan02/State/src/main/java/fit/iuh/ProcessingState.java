package fit.iuh;

public class ProcessingState    implements OrderState{
    public void handleRequest(OrderContext context) {
        System.out.println("Trạng thái: ĐANG XỬ LÝ. Đang đóng gói và vận chuyển...");
        context.setState(new DeliveredState());
    }
}
