package fit.iuh;

public class Main {
    public static void main(String[] args) {
        // Hệ thống cũ
        XmlSystem oldSystem = new XmlSystem();

        // Web Service muốn dùng JSON nhưng phải thông qua Adapter để nói chuyện với hệ thống cũ
        JsonService adapter = new XmlToJsonAdapter(oldSystem);

        String inputJson = "{ 'user': 'admin' }";
        adapter.requestJson(inputJson);
    }
}