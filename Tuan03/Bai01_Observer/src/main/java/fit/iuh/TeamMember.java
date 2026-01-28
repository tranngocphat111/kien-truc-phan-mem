package fit.iuh;

public class TeamMember implements Observer {
    private String name;
    public TeamMember(String name) { this.name = name; }

    @Override
    public void update(String message) {
        System.out.println("Gửi đến Thành viên " + name + ": [Task Update] " + message);
    }
}
