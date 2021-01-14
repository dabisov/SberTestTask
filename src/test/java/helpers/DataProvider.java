package helpers;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class DataProvider {

    @org.testng.annotations.DataProvider(name = "data-provider")
    public Object[][] getData() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {

//        Utils.changeValues("input.xml", "output.xml", 10);
        Data data = Utils.getData("output.xml");
        Object[][] array = new Object[data.getManufacturers().size()][2];

        for(int i=0; i <data.getManufacturers().size(); i++){
            array[i][0]=data.getManufacturers().get(i);
            array[i][1]=data.getUpperPrice();
        }

        return array;
    }

//    @org.testng.annotations.DataProvider(name = "data-provider-alone")
//    public Object[][] getDataAlone() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
//
//        Data data = Utils.getData("output.xml");
//        Object[][] array = new Object[1][2];
//
//        array[0][0]=data.getManufacturers().get(0);
//        array[0][1]=data.getUpperPrice();
//
//        return array;
//    }

}
