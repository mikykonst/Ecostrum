package com.journaldev.passingdatabetweenfragments.XML;

import com.journaldev.passingdatabetweenfragments.Model.Price;
import com.journaldev.passingdatabetweenfragments.Model.Product;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class XMLParser {

    String LOG_TAG = "XMLParser";

    StringReader is;

    ArrayList<Product> products;
    private Product product;
    private String text;
    private Price price;
    String getTypeStr;

    public XMLParser() {
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> parseXML(String str) {

        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            is = new StringReader(str);
            parser.setInput(is);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("product")) {
                            product = new Product("", "", "", new ArrayList<Price>(), "", "", "","");
                            price = new Price();
                            product.setType(getTypeStr);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("name") && parser.getDepth() == 4) {
                            getTypeStr = text;
                        } else if (tagname.equalsIgnoreCase("product")) {
                            products.add(product);
                        } else if (tagname.equals("kod1С") && parser.getDepth() > 4) {
                            product.setId(text);
                        } else if (tagname.equalsIgnoreCase("name") && parser.getDepth() > 4) {
                            product.setName(text);
                        } else if (tagname.equalsIgnoreCase("model") && parser.getDepth() > 4) {
                            product.setModel(text);
                        } else if (tagname.equalsIgnoreCase("ostatok") && parser.getDepth() > 4) {
                            product.setOstatok(text);
                        } else if (tagname.equalsIgnoreCase("price1Name")) {
                            price.setName(text);
                        } else if (tagname.equalsIgnoreCase("price1Value")) {
                            price.setValue(text);
                            product.setPrice(price);
                        } else if (tagname.equalsIgnoreCase("price2Name")) {
                            price = new Price();
                            price.setName(text);
                        } else if (tagname.equalsIgnoreCase("price2Value")) {
                            price.setValue(text);
                            product.setPrice(price);
                        } else if (tagname.equalsIgnoreCase("price3Name")) {
                            price = new Price();
                            price.setName(text);
                        } else if (tagname.equalsIgnoreCase("price3Value")) {
                            price.setValue(text);
                            product.setPrice(price);
                        }else if (tagname.equalsIgnoreCase("price4Name")) {
                            price = new Price();
                            price.setName(text);
                        } else if (tagname.equalsIgnoreCase("price4Value")) {
                            price.setValue(text);
                            product.setPrice(price);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
