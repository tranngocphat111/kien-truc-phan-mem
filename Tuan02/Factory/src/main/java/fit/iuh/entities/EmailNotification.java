package fit.iuh.entities;

import fit.iuh.interfaces.Notification;

public class EmailNotification implements Notification {
    public void send(String message) {
        System.out.println("Gửi EMAIL với nội dung: " + message);
    }
}
