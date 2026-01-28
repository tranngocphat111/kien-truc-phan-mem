package fit.iuh;

public class XmlToJsonAdapter implements JsonService {
    private XmlSystem xmlSystem;

    public XmlToJsonAdapter(XmlSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
    }

    @Override
    public void requestJson(String jsonData) {
        // Giả lập việc chuyển đổi từ JSON sang XML
        System.out.println("Adapter: Đang chuyển đổi JSON sang XML...");
        String xmlData = "<data>" + jsonData.replace("{", "").replace("}", "") + "</data>";

        // Gọi hệ thống XML
        xmlSystem.receiveXml(xmlData);
    }
}