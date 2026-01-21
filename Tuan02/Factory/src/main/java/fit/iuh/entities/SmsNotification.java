package fit.iuh.entities;

import fit.iuh.interfaces.Notification;

public class SmsNotification implements Notification {
    public void send(String message) {
        System.out.println("Gửi tin nhắn SMS với nội dung: " + message);
    }
}