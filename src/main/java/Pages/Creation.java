package Pages;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

public class Creation
{
    protected ArrayList <Creation_body> bodies;
    protected Document xml_document;

    public Creation()
    {
        this.bodies = parse_excel_sheet("C:\\Users\\ahmed\\Desktop\\Creation.xlsx");
    }
    public Object create_work_order(String Request_type) throws ParserConfigurationException, IOException, TransformerException, SAXException, XPathExpressionException {
        String body= get_creaion_by_request_type(Request_type);
        converting_from_string_to_XML(body);
        body= randomize_OM_Order_ID();
        body= update_Service_Number();
        String work_order_id=send_creation_request(body);
        return work_order_id;
    }
    public String get_creaion_by_request_type(String Request_type)
    {
        String request_body= null;
        for(int i = 0; i < this.bodies.size();i++)
        {
            if(this.bodies.get(i).Request_Type.equals(Request_type))
            {
                request_body = this.bodies.get(i).creation;
            }
        }
        return request_body;
    }
    public ArrayList<Creation_body> parse_excel_sheet(String excelFilePath)
    {
        this.bodies = new ArrayList<Creation_body>();
        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // First sheet
            int rowCount = sheet.getPhysicalNumberOfRows();
            Row headerRow = sheet.getRow(0);
            String label1 = headerRow.getCell(0).getStringCellValue();
            String label2 = headerRow.getCell(1).getStringCellValue();
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String value1 = row.getCell(0).toString();
                    String value2 = row.getCell(1).toString();
                    Creation_body body = new Creation_body(value1,value2);
                    this.bodies.add(body);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.bodies;
    }
    public void converting_from_string_to_XML(String xml2) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String xml = xml2.toString().replace("\uFEFF", "").trim();
        Document doc = builder.parse(new InputSource(new StringReader(xml.trim())));
        this.xml_document= doc;
    }
    public String randomize_OM_Order_ID() throws TransformerException {
        Node omOrderIDNode = this.xml_document.getElementsByTagName("OMOrderID").item(0);
        Random random = new Random();
        long randomNumber = 1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L);
        String randomNumberString = String.valueOf(randomNumber);
        omOrderIDNode.setTextContent(randomNumberString);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(this.xml_document), new StreamResult(writer));
        String updatedXML = writer.getBuffer().toString();
        return updatedXML;
    }
    public String update_Service_Number() throws TransformerException {
        Node omOrderIDNode = this.xml_document.getElementsByTagName("ServiceNo").item(0);
        omOrderIDNode.setTextContent("37278097");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(this.xml_document), new StreamResult(writer));
        String updatedXML = writer.getBuffer().toString();
        return updatedXML;
    }
    public String send_creation_request(String request_body)throws IOException
    {
        URL url = new URL("http://10.19.35.91:8003/HiveAPIs/resources/hivews/CreateOrder");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type","application/xml");
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes(request_body);
        // System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = bf.readLine())!= null)
        {
            responseBuilder.append(line);
        }
        bf.close();
        String response = responseBuilder.toString();
        JSONObject json = new JSONObject(response);
        String workOrderNo = json.getString("workOrderNo");
        return workOrderNo;
    }
}