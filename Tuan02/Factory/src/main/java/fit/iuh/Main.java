package fit.iuh;

import fit.iuh.interfaces.Notification;

public class Main {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();

        // Muốn gửi Email
        Notification n1 = factory.createNotification("email");
        n1.send("Chào mừng bạn đến với cửa hàng nước hoa!");

        // Muốn gửi SMS
        Notification n2 = factory.createNotification("sms");
        n2.send("Đơn hàng của bạn đang được giao.");
    }
}