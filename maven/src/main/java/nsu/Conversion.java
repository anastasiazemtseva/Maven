package nsu;
import kong.unirest.Unirest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class Conversion {
    private String date;
    private String currencyName;

    public Conversion(String data, String name) throws ExceptionEmptyImp {
        if ((data.isBlank()) || (name.isBlank())) {
            throw new ExceptionEmptyImp();
        }
        this.date = data;
        this.currencyName = name.toUpperCase();
    }

    public String getDate() {
        return date;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void getExchangeRate() throws ParserConfigurationException, IOException, SAXException, ExceptionNotFound {
        String url = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date;

        String data = Unirest.get(url).asString().getBody();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(data)));

        NodeList currenciesList = doc.getElementsByTagName("Valute");
        for (int i = 0; i < currenciesList.getLength(); i++) {
            Element cur = (Element) currenciesList.item(i);
            if (cur.getElementsByTagName("CharCode").item(0).getTextContent().equals(currencyName)) {
                String name = cur.getElementsByTagName("Name").item(0).getTextContent();
                String value = cur.getElementsByTagName("Value").item(0).getTextContent();
                System.out.println("\n1 " + name + " = " + value + " Российских рублей");
                return;
            }
        }
        throw new ExceptionNotFound();
    }
}
