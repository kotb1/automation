package org.example;

import Pages.Creation;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws XPathExpressionException, ParserConfigurationException, IOException, TransformerException, SAXException {
        Creation x = new Creation();
        System.out.println(x.create_work_order("FTTHUnRchReSurvey"));

    }
}
