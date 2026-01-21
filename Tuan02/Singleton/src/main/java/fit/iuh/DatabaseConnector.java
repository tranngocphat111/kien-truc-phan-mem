package fit.iuh;

// 1. Lớp Singleton
class DatabaseConnector {
    // Biến static lưu trữ instance duy nhất
    private static DatabaseConnector instance;

    // Constructor private để ngăn không cho tạo mới bằng từ khóa 'new'
    private DatabaseConnector() {
        System.out.println("--- Đang khởi tạo kết nối Database (Chỉ chạy 1 lần) ---");
    }

    // Phương thức public để lấy instance
    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Thực thi lệnh: " + sql);
    }
}

