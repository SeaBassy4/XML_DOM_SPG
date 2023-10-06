import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;
import java.io.FileOutputStream;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
public class Main{
public static void main(String[] args) {
        try {
        File inputFile = new File("src\\sales.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el Departamento que desea modificar: ");
        String department = scanner.nextLine().trim();
        System.out.print("Ingrese el porcentaje que desea incrementar (entre 5 y 15): ");
        int percent = Integer.parseInt(scanner.nextLine().trim());
        if (percent < 5 || percent > 15) {
        System.out.println("El porcentaje ingresado no cumplio con los parametros.");
        return;
        }
            NodeList sale_Records = doc.getElementsByTagName("sale_record");
            for (int i = 0; i < sale_Records.getLength(); i
                    ++) {
                Node saleRecord = sale_Records.item(i);
                if (saleRecord.getNodeType() == Node.ELEMENT_NODE) {
                    Element saleElement = (Element) saleRecord;
                    String saleDepartment = saleElement.getElementsByTagName("department").item(0).getTextContent();
                    if (saleDepartment.equalsIgnoreCase(department)) {
                        double verkaufenValue = Double.parseDouble(saleElement.getElementsByTagName("sales").item(0).getTextContent());
                        double neueValue = verkaufenValue * (1+(percent/100.0));
                        saleElement.getElementsByTagName("sales").item(0).setTextContent(String.format("%.2f", neueValue));
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream("new_sales.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
        e.printStackTrace();
        }
}
}
