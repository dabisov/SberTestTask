package helpers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.NODESET;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Utils {

    private static NodeList setValue(NodeList nodeList, int order){
        Element element;
        for(int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            element.setTextContent(String.valueOf(Integer.parseInt(element.getTextContent()) * order));
        }
        return nodeList;
    }

    public static void changePrices(String input, String output, int order) {
        try {
            File xmlFile = new File(input);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalizeDocument();

            NodeList minPrices = document.getElementsByTagName("Min");
            NodeList maxPrices = document.getElementsByTagName("Max");

            minPrices = setValue(minPrices, order);
            maxPrices = setValue(maxPrices, order);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(output));
            transformer.transform(source, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Data getData(String filepath) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        File xmlFile = new File(filepath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        Node upperLimitNode = (Node) xpath
                .compile("//Price/Max")
                .evaluate(document, NODE);

        NodeList nameList = (NodeList) xpath
                .compile("//Manufacturer[contains(@products,'notebook')]/Name")
                .evaluate(document, NODESET);

        NodeList minList = (NodeList) xpath
                .compile("//Manufacturer[contains(@products,'notebook')]/PriceLimit/Min")
                .evaluate(document, NODESET);

        NodeList maxList = (NodeList) xpath
                .compile("//Manufacturer[contains(@products,'notebook')]/PriceLimit/Max")
                .evaluate(document, NODESET);


        int upperPriceValue = Integer.parseInt(upperLimitNode.getTextContent());
        ArrayList<Manufacturer> manufacturers = new ArrayList<>();

        for (int i = 0; i < nameList.getLength(); i++) {
            manufacturers.add(new Manufacturer(
                    nameList.item(i).getTextContent(),
                    Integer.parseInt(minList.item(i).getTextContent()),
                    Integer.parseInt(maxList.item(i).getTextContent())));
        }
        return new Data(upperPriceValue, manufacturers);
    }

    public static double parseToDouble(String line){
        final String regex = "(\\d+\\.\\d+|\\d+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group());
        }
        return 0d;
    }

    public static void saveNotebookToXml(Notebook notebook, String filePath){
        try {
            JAXBContext context = JAXBContext.newInstance(Notebook.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(notebook, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
