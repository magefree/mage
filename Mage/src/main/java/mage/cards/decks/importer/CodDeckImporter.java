package mage.cards.decks.importer;

import static javax.xml.xpath.XPathConstants.NODESET;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;

public class CodDeckImporter extends DeckImporter {

  private XPathFactory xpathFactory = XPathFactory.newInstance();
  private DocumentBuilder builder = getDocumentBuilder();

  @Override
  public DeckCardLists importDeck(String filename, StringBuilder errorMessages) {
    try {
      Document doc = getXmlDocument(filename);
      DeckCardLists decklist = new DeckCardLists();

      List<Node> mainCards = getNodes(doc, "/cockatrice_deck/zone[@name='main']/card");
      decklist.setCards(mainCards.stream()
          .flatMap(toDeckCardInfo(getCardLookup(), errorMessages))
          .collect(Collectors.toList()));

      List<Node> sideboardCards = getNodes(doc, "/cockatrice_deck/zone[@name='side']/card");
      decklist.setSideboard(sideboardCards.stream()
          .flatMap(toDeckCardInfo(getCardLookup(), errorMessages))
          .collect(Collectors.toList()));

      getNodes(doc, "/cockatrice_deck/deckname")
          .forEach(n -> decklist.setName(n.getTextContent().trim()));

      return decklist;
    } catch (Exception e) {
      logger.error("Error loading deck", e);
      errorMessages.append("There was an error loading the deck.");
      return new DeckCardLists();
    }
  }

  private static int getQuantityFromNode(Node node) {
    Node numberNode = node.getAttributes().getNamedItem("number");
    if (numberNode == null) {
      return 1;
    }
    try {
      return Math.min(100, Math.max(1, Integer.parseInt(numberNode.getNodeValue())));
    } catch (NumberFormatException e) {
      return 1;
    }
  }

  private static Function<Node, Stream<DeckCardInfo>> toDeckCardInfo(CardLookup lookup, StringBuilder errors) {
    return node -> {
      String name = node.getAttributes().getNamedItem("name").getNodeValue().trim();
      Optional<CardInfo> cardInfo = lookup.lookupCardInfo(name);
      if (cardInfo.isPresent()) {
        CardInfo info = cardInfo.get();
        return Collections.nCopies(
            getQuantityFromNode(node),
            new DeckCardInfo(info.getName(), info.getCardNumber(), info.getSetCode())).stream();
      } else {
        errors.append("Could not find card: '").append(name).append("'\n");
        return Stream.empty();
      }
    };
  }

  private List<Node> getNodes(Document doc, String xpathExpression) throws XPathExpressionException {
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
