package mage.cards.decks.importer;

import mage.cards.decks.DeckCardInfo;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodDeckImporter extends XmlDeckImporter {

    /**
     * @param filename
     * @param errorMessages
     * @param saveAutoFixedFile do not supported for current format
     * @return
     */
    @Override
    public DeckCardLists importDeck(String fileName, StringBuilder errorMessages, boolean saveAutoFixedFile) {
        try {
            Document doc = getXmlDocument(fileName);
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

}
