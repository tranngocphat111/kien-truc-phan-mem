package fit.iuh;

import fit.iuh.entities.EmailNotification;
import fit.iuh.entities.SmsNotification;
import fit.iuh.interfaces.Notification;

public class NotificationFactory {
    public Notification createNotification(String type) {
        if (type == null || type.isEmpty()) return null;

        switch (type.toLowerCase()) {
            case "email":
                return new EmailNotification();
            case "sms":
                return new SmsNotification();
            default:
                throw new IllegalArgumentException("Loại thông báo không hợp lệ!");
        }
    }
}
