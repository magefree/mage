package mage.deck;

import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Alchemy Format - Digital-only format with rebalanced cards
 *
 * Alchemy is a digital format where Wizards of the Coast rebalances cards
 * from Standard to improve the digital metagame. Cards are rebalanced by
 * changing mechanics, costs, or effects.
 *
 * This implementation includes automatic card substitution:
 * When building an Alchemy deck, paper versions of rebalanced cards are
 * automatically replaced with their Alchemy counterparts (prefixed with "A-").
 *
 * @author Vernon
 */
public class Alchemy extends Constructed {

    private static final Logger logger = Logger.getLogger(Alchemy.class);

    // Mapping of paper card names to their Alchemy rebalanced versions
    private static final Map<String, String> ALCHEMY_REBALANCES = new HashMap<>();

    static {
        // Format: paper version -> alchemy version
        ALCHEMY_REBALANCES.put("Luminarch Aspirant", "A-Luminarch Aspirant");
        // Additional rebalances can be added here as they're implemented
    }

    public Alchemy() {
        super("Constructed - Alchemy");

        // Alchemy includes all cards legal in Standard at the time
        // plus the digital-only Alchemy set with rebalanced cards
        setCodes.add("ALC"); // Alchemy digital-only set
        setCodes.addAll(Standard.makeLegalSets()); // Standard legal sets

        // No additional bans beyond Standard
    }

    @Override
    public boolean validate(Deck deck) {
        // Replace paper versions of rebalanced cards with Alchemy versions
        replaceRebalancedCards(deck);

        // Then perform standard Alchemy format validation
        return super.validate(deck);
    }

    /**
     * Automatically replaces paper versions of rebalanced cards with their Alchemy counterparts.
     * This happens transparently during deck validation.
     *
     * @param deck The deck to process
     */
    private void replaceRebalancedCards(Deck deck) {
        // Replace in main deck
        replacePaperCardsWithAlchemy(deck.getCards());

        // Replace in sideboard
        replacePaperCardsWithAlchemy(deck.getSideboard());
    }

    /**
     * Helper method to replace paper cards with Alchemy versions in a card collection.
     *
     * @param cards The collection of cards to process
     */
    private void replacePaperCardsWithAlchemy(Cards cards) {
        if (cards == null || cards.isEmpty()) {
            return;
        }

        Cards cardsToRemove = new CardsImpl();
        Cards cardsToAdd = new CardsImpl();

        for (Card card : cards) {
            if (hasAlchemyRebalance(card.getName())) {
                String alchemyCardName = getAlchemyCardName(card.getName());

                // Find the Alchemy version of the card
                CardInfo alchemyCardInfo = CardRepository.instance.findCard(alchemyCardName, "ALC");
                if (alchemyCardInfo != null) {
                    try {
                        Card alchemyCard = alchemyCardInfo.getCard();
                        cardsToRemove.add(card);
                        cardsToAdd.add(alchemyCard);
                        logger.debug("Alchemy format: Replaced \"" + card.getName() + "\" with \"" + alchemyCard.getName() + "\"");
                    } catch (Exception e) {
                        logger.warn("Could not create Alchemy card: " + alchemyCardName, e);
                    }
                } else {
                    logger.warn("Alchemy card not found: " + alchemyCardName);
                }
            }
        }

        // Remove original paper cards and add Alchemy versions
        cards.removeAll(cardsToRemove);
        cards.addAll(cardsToAdd);
    }

    /**
     * Get the Alchemy rebalanced version of a card if it exists.
     * Used during deck validation to replace paper cards with their Alchemy equivalents.
     *
     * @param paperCardName The name of the paper version card
     * @return The name of the Alchemy rebalanced card, or the original name if no rebalance exists
     */
    public static String getAlchemyCardName(String paperCardName) {
        return ALCHEMY_REBALANCES.getOrDefault(paperCardName, paperCardName);
    }

    /**
     * Check if a card has an Alchemy rebalanced version.
     *
     * @param cardName The card name to check
     * @return true if this card has an Alchemy rebalance, false otherwise
     */
    public static boolean hasAlchemyRebalance(String cardName) {
        return ALCHEMY_REBALANCES.containsKey(cardName);
    }

    /**
     * Get all paper cards that have Alchemy rebalances.
     *
     * @return Set of paper card names with Alchemy rebalances
     */
    public static Set<String> getRebalancedCardNames() {
        return new HashSet<>(ALCHEMY_REBALANCES.keySet());
    }
}


