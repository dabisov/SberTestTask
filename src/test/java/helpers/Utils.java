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

import static javax.xml.xpath.XPathConstants.NODE;
import static javax.xml.xpath.XPathConstants.NODESET;

public class Utils {

    public static void changeValues(String input, String output, int order) {
        try {
            File xmlFile = new File(input);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.normalizeDocument();

            NodeList minPrices = document.getElementsByTagName("Min");
            NodeList maxPrices = document.getElementsByTagName("Max");

            Element element;
            for(int i = 0; i < maxPrices.getLength(); i++) {
                if (i < minPrices.getLength()) {
                    element = (Element) minPrices.item(i);
                    minPrices.item(i).setTextContent(String.valueOf(Integer.parseInt(element.getTextContent()) * order));
                }
                element = (Element) maxPrices.item(i);
                element.setTextContent(String.valueOf(Integer.parseInt(element.getTextContent()) * order));
            }

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

        XPathFactory xpathFactory = XPathFactory.newDefaultInstance();
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

}
