package APIS;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Reopen_Ticket
{
    protected Document xml_document;
    //private String Reopen_body="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcc=\"http://FCCHiveWS/\"><soapenv:Header/><soapenv:Body><fcc:ReopenWoFCC><WorkOrderInfo><addList><attCode>?</attCode><attValue>?</attValue></addList><cabinetNo>?</cabinetNo><cityCode>?</cityCode><complainNo>7723074574</complainNo><detractorFlag>?</detractorFlag><dpLatitude>?</dpLatitude><dpLongitude>?</dpLongitude><exchCode>?</exchCode><initiator>?</initiator><notes>?</notes><priCode>?</priCode><requestType>?</requestType><telNo>?</telNo></WorkOrderInfo></fcc:ReopenWoFCC></soapenv:Body></soapenv:Envelope>";
    //private String reopen_url= "http://10.1.1.80:7003/FCCHiveWS/WSPort";
    private String Reopen_body="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcc=\"http://fccwfminteg/\"><soapenv:Header/><soapenv:Body><fcc:ReopenWo><WorkOrderInfo><addList><attCode>?</attCode><attValue>?</attValue></addList><cabinetNo>?</cabinetNo><cityCode>?</cityCode><complainNo>7723074574</complainNo><detractorFlag>?</detractorFlag><dpLatitude>?</dpLatitude><dpLongitude>?</dpLongitude><exchCode>?</exchCode><initiator>?</initiator><notes>?</notes><priCode>?</priCode><requestType>?</requestType><telNo>?</telNo></WorkOrderInfo></fcc:ReopenWo></soapenv:Body></soapenv:Envelope>";
    private String reopen_url= "http://10.19.35.91:8003/FCCWFMInteg-FCCWFMInteg-context-root/FCCWFMIntegPort";
    public String reopen_ticket(String refrence_id) throws Exception {
        converting_from_string_to_XML();
        return send_reopen_request_Maintenance(update_complain_number(refrence_id),reopen_url);
    }
    public String reopen_archived_ticket(String body) throws Exception {
        converting_from_string_to_XML();
        updateWorkOrderInfo(xml_document,replaceCreateWithReopen(body));
        return send_reopen_request_Maintenance(printDocument(xml_document),reopen_url);
    }
    public void converting_from_string_to_XML() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xml = Reopen_body.toString().replace("\uFEFF", "").trim();
        Document doc = builder.parse(new InputSource(new StringReader(xml.trim())));
        this.xml_document= doc;
    }
    public String parseSoapResponse(String responseXml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // مهم عشان فيه namespaces
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(responseXml));
        Document doc = builder.parse(is);

        // هنجيب الـ "Result" من أي namespace
        NodeList resultNodes = doc.getElementsByTagNameNS("*", "Result");
        if (resultNodes.getLength() > 0) {
            return resultNodes.item(0).getTextContent();
        } else {
            return null; // لو مفيش Result
        }
    }
    public String update_complain_number(String refrence_id) throws TransformerException {
        Node omOrderIDNode = this.xml_document.getElementsByTagName("complainNo").item(0);
        omOrderIDNode.setTextContent(refrence_id);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(this.xml_document), new StreamResult(writer));
        String updatedXML = writer.getBuffer().toString();
        return updatedXML;
    }
    public String update_work_order_info(String body) throws TransformerException {
        Node omOrderIDNode = this.xml_document.getElementsByTagName("WorkOrderInfo").item(0);
        omOrderIDNode.setTextContent(body);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(this.xml_document), new StreamResult(writer));
        String updatedXML = writer.getBuffer().toString();
        return updatedXML;
    }
    public String send_reopen_request_Maintenance(String request_body, String URL) throws Exception {
        // SOAP endpoint URL
        URL url = new URL(URL);// http://10.19.35.91:8003/FCCWFMInteg-FCCWFMInteg-context-root/FCCWFMIntegPort
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // SOAP requires XML
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

        // Send SOAP Request Body
        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(request_body);
        }

        // Read response (XML)
        BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            responseBuilder.append(line);
        }
        bf.close();

        String responseXml = responseBuilder.toString();

        // مثال: لو الـ response فيه <workOrderNo>12345</workOrderNo>
        String workOrderNo= parseSoapResponse(responseXml);

        return workOrderNo;
    }
    public static String replaceCreateWithReopen(String xml) {
        int start = xml.indexOf("<WorkOrderInfo>");
        int end = xml.indexOf("</WorkOrderInfo>") + "</WorkOrderInfo>".length();

        String workOrderInfoData = xml.substring(start, end);

        return workOrderInfoData;
    }
    public static void updateWorkOrderInfo(Document document, String newWorkOrderInfoXml) {
        try {
            // Parse the new WorkOrderInfo string as a mini XML Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document newPartDoc = builder.parse(new InputSource(new StringReader(newWorkOrderInfoXml)));

            // Get the new <WorkOrderInfo> element from it
            Node newWorkOrderInfo = document.importNode(
                    newPartDoc.getDocumentElement(), true);

            // Find the existing <WorkOrderInfo> in the main document
            NodeList nodes = document.getElementsByTagName("WorkOrderInfo");
            if (nodes.getLength() > 0) {
                Node oldWorkOrderInfo = nodes.item(0);
                Node parent = oldWorkOrderInfo.getParentNode();
                parent.replaceChild(newWorkOrderInfo, oldWorkOrderInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String printDocument(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Print to console
            transformer.transform(new DOMSource(doc), new StreamResult(System.out));

            // Or convert to a String
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String xmlString = writer.toString();

            System.out.println("\n--- XML as String ---\n" + xmlString);
            return xmlString;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

