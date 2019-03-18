package mage.cards.decks.importer;

import static javax.xml.xpath.XPathConstants.NODESET;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class XmlDeckImporter extends DeckImporter {

  private XPathFactory xpathFactory = XPathFactory.newInstance();
  private DocumentBuilder builder = getDocumentBuilder();

  protected List<Node> getNodes(Document doc, String xpathExpression) throws XPathExpressionException {
    NodeList nodes =  (NodeList) xpathFactory.newXPath().evaluate(xpathExpression, doc, NODESET);
    ArrayList<Node> list = new ArrayList<>();
    for (int i = 0; i < nodes.getLength(); i++) {
      list.add(nodes.item(i));
    }
    return list;
  }

  private DocumentBuilder getDocumentBuilder() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
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
