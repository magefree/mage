package mage.cards.decks.importer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.xpath.XPathConstants.NODESET;

public abstract class XmlDeckImporter extends DeckImporter {

    private final XPathFactory xpathFactory = XPathFactory.newInstance();
    private final DocumentBuilder builder = getDocumentBuilder();

    protected List<Node> getNodes(Document doc, String xpathExpression) throws XPathExpressionException {
        NodeList nodes = (NodeList) xpathFactory.newXPath().evaluate(xpathExpression, doc, NODESET);
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            list.add(nodes.item(i));
        }
        return list;
    }

    private DocumentBuilder getDocumentBuilder() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // security fix from https://stackoverflow.com/a/59736162/1276632 to disable external data download
            // REDHAT
            // https://www.blackhat.com/docs/us-15/materials/us-15-Wang-FileCry-The-New-Age-Of-XXE-java-wp.pdf
            factory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            // OWASP
            // https://cheatsheetseries.owasp.org/cheatsheets/XML_External_Entity_Prevention_Cheat_Sheet.html
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            // Disable external DTDs as well
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            // and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            return factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException();
        }
    }

    protected Document getXmlDocument(String filename) throws IOException {
        try {
            return builder.parse(new File(filename));
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }

}
