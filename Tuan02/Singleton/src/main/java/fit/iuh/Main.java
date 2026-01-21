package fit.iuh;

public class Main {
    public static void main(String[] args) {
        // Lấy kết nối lần 1
        DatabaseConnector db1 = DatabaseConnector.getInstance();
        db1.query("SELECT * FROM products");

        // Lấy kết nối lần 2
        DatabaseConnector db2 = DatabaseConnector.getInstance();
        db2.query("SELECT * FROM orders");

        // Kiểm tra xem db1 và db2 có phải là một không
        if(db1 == db2) {
            System.out.println("db1 và db2 là cùng một instance.");
        } else {
            System.out.println("db1 và db2 là các instance khác nhau.");
        }
    }
}