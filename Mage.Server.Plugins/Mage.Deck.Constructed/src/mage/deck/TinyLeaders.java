package mage.deck;

import mage.cards.*;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorErrorType;
import mage.constants.CardType;
import mage.filter.FilterMana;
import mage.game.GameTinyLeadersImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JRHerlehy
 */
public class TinyLeaders extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();

    public TinyLeaders() {
        super("Tiny Leaders");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
        //Banned list from tinyleaders.blodspot.ca/p/ban-list.html
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Black Lotus");
        banned.add("Black Vise");
        banned.add("Channel");
        banned.add("Codie, Vociferous Codex");
        banned.add("Counterbalance");
        banned.add("Demonic Tutor");
        banned.add("Earthcraft");
        banned.add("Edric, Spymaster of Trest");
        banned.add("Fastbond");
        banned.add("Goblin Recruiter");
        banned.add("Grindstone");
        banned.add("Hermit Druid");
        banned.add("High Tide");
        banned.add("Imperial Seal");
        banned.add("Library of Alexandria");
        banned.add("Karakas");
        banned.add("Mana Crypt");
        banned.add("Mana Drain");
        banned.add("Mana Vault");
        banned.add("Metalworker");
        banned.add("Mind Twist");
        banned.add("Mishra's Workshop");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Najeela, the Blade-Blossom");
        banned.add("Necropotence");
        banned.add("Sisay, Weatherlight Captain");
        banned.add("Skullclamp");
        banned.add("Sol Ring");
        banned.add("Strip Mine");
        banned.add("Survival of the Fittest");
        banned.add("Sword of Body and Mind");
        banned.add("Thassa's Oracle");
        banned.add("The Tabernacle at Pendrell Vale");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Timetwister");
        banned.add("Tolarian Academy");
        banned.add("Umezawa's Jitte");
        banned.add("Vampiric Tutor");
        banned.add("Wheel of Fortune");
        banned.add("Yawgmoth's Will");

        // TODO: Karn Liberated can't be used in TinyLeaders game (wrong commanders init like missing watchers)
        //  GameTinyLeadersImpl must extends GameCommanderImpl, not GameImpl
        banned.add("Karn Liberated");

        //Additionally, these Legendary creatures cannot be used as Commanders
        bannedCommander.add("Erayo, Soratami Ascendant");
        bannedCommander.add("Rofellos, Llanowar Emissary");
        bannedCommander.add("Derevi, Empyrical Tactician");
    }

    @Override
    public int getDeckMinSize() {
        return 49; // commander gives from deck name
    }

    @Override
    public int getSideboardMinSize() {
        return 0;
    }

    /**
     * @param deck
     * @return - True if deck is valid
     */
    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;
        errorsList.clear();

        if (deck.getCards().size() != getDeckMinSize()) {
            addError(DeckValidatorErrorType.DECK_SIZE, "Deck", "Must contain " + getDeckMinSize() + " cards: has " + deck.getCards().size() + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        counts.put(deck.getName(), 1); // add the commander to the counts, so it can't be in the deck or sideboard again
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        valid = checkCounts(1, counts) && valid;

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                addError(DeckValidatorErrorType.BANNED, bannedCard, "Banned", true);
                valid = false;
            }
        }

        if (deck.getSideboard().size() <= 10) {
            Card commander = GameTinyLeadersImpl.getCommanderCard(deck.getName(), null);
            /**
             * 905.5b - Each card must have a converted mana cost of three of
             * less. Cards with {X} in their mana cost count X as zero. Split
             * and double-face cards are legal only if both of their halves
             * would be legal independently.
             */

            if (commander == null || commander.getManaValue() > 3) {
                if (commander == null) {
                    if (deck.getName() == null) {
                        addError(DeckValidatorErrorType.PRIMARY, "Leader", "You have to save your deck with the leader card name entered to the DECK NAME field of the DECK EDITOR (top left) so that XMage knows your leader."
                                + "(You can use the \"Sultai\" for a UBG (3/3) default Commander or \"Glass\" for a colorless 3/3 default Commander.)");
                    } else {
                        addError(DeckValidatorErrorType.PRIMARY, "Leader", "Leader [" + deck.getName() + "] not found. You have to enter the name of the leader card into the DECK NAME field of the DECK EDITOR (top left). Check your spelling "
                                + "(use the \"Sultai\" for a UBG (3/3) default Commander or \"Glass\" for a colorless (3/3) default Commander)");

                    }
                }
                if (commander != null && commander.getManaValue() > 3) {
                    addError(DeckValidatorErrorType.PRIMARY, "Leader", "Commanders mana value is greater than 3");
                }
                return false;
            }
            if ((commander.hasCardTypeForDeckbuilding(CardType.CREATURE) && commander.isLegendary())
                    || commander.hasCardTypeForDeckbuilding(CardType.PLANESWALKER)) {
                if (!bannedCommander.contains(commander.getName())) {
                    FilterMana color = commander.getColorIdentity();
                    for (Card card : deck.getCards()) {
                        if (!isCardFormatValid(card, commander, color)) {
                            valid = false;
                        }
                    }
                    for (Card card : deck.getSideboard()) {
                        if (!isCardFormatValid(card, commander, color)) {
                            valid = false;
                        }
                    }
                } else {
                    addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Commander banned (" + commander.getName() + ')', true);
                    valid = false;
                }
            } else {
                addError(DeckValidatorErrorType.PRIMARY, commander.getName(), "Commander invalide (" + commander.getName() + ')', true);
                valid = false;
            }
        } else {
            addError(DeckValidatorErrorType.PRIMARY, "Commander", "Sideboard must contain only a maximum of 10 sideboard cards (the Tiny Leader name must be written to the deck name)");
            valid = false;
        }
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    addError(DeckValidatorErrorType.WRONG_SET, card.getName(), "Not allowed Set " + card.getExpansionSetCode(), true);
                    valid = false;
                }
            }
        }
        return valid;
    }

    private boolean isCardFormatValid(Card card, Card commander, FilterMana color) {
        if (!cardHasValideColor(color, card)) {
            addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid color (" + commander.getName() + ')', true);
            return false;
        }

        // 906.5b
        // Each card must have a converted mana cost of three or less. Cards with {x} in their mana cost count X
        // as zero for this purpose. Split cards are legal only if both of their halves would be legal independently.
        List<Integer> costs = new ArrayList<>();
        if (card instanceof SplitCard) {
            costs.add(((SplitCard) card).getLeftHalfCard().getManaValue());
            costs.add(((SplitCard) card).getRightHalfCard().getManaValue());
        } else if (card instanceof ModalDoubleFacedCard) {
            costs.add(((ModalDoubleFacedCard) card).getLeftHalfCard().getManaValue());
            costs.add(((ModalDoubleFacedCard) card).getRightHalfCard().getManaValue());
        } else {
            costs.add(card.getManaValue());
        }

        return costs.stream().allMatch(cost -> {
            if (cost > 3) {
                addError(DeckValidatorErrorType.OTHER, card.getName(), "Invalid cost (" + cost + ')', true);
                return false;
            }
            return true;
        });
    }

    /**
     * @param commander FilterMana object with Color Identity of Commander set
     * @param card      Card to validate
     * @return True if card has a valid color identity
     */
    public boolean cardHasValideColor(FilterMana commander, Card card) {
        FilterMana cardColor = card.getColorIdentity();
        return !(cardColor.isBlack() && !commander.isBlack()
                || cardColor.isBlue() && !commander.isBlue()
                || cardColor.isGreen() && !commander.isGreen()
                || cardColor.isRed() && !commander.isRed()
                || cardColor.isWhite() && !commander.isWhite());
    }

}
