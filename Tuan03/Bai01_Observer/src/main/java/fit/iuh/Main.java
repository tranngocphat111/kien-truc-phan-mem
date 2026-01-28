package fit.iuh;

public class Main {
    public static void main(String[] args) {
        Subject stockMarket = new Subject();
        Subject taskManager = new Subject();

        stockMarket.subscribe(new Investor("An"));
        stockMarket.subscribe(new Investor("Bình"));

        taskManager.subscribe(new TeamMember("Chi"));

        // Giả lập thay đổi
        stockMarket.notifyObservers("Giá cổ phiếu VNM đã tăng 5%!");
        taskManager.notifyObservers("Task 'Lập trình API' đã chuyển sang DONE.");
    }
}