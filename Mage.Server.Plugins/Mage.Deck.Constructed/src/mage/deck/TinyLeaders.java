
package mage.deck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.SplitCard;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.constants.SetType;
import mage.filter.FilterMana;
import mage.game.GameTinyLeadersImpl;

/**
 *
 * @author JRHerlehy
 */
public class TinyLeaders extends Constructed {

    protected List<String> bannedCommander = new ArrayList<>();

    public TinyLeaders() {
        this("Tiny Leaders");
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType() != SetType.CUSTOM_SET) {
                setCodes.add(set.getCode());
            }
        }
        //Banned list from tinyleaders.blodspot.ca/p/ban-list.html
        //Ban list updated as of 11/08/14
        banned.add("Ancestral Recall");
        banned.add("Balance");
        banned.add("Black Lotus");
        banned.add("Channel");
        banned.add("Counterbalance");
        banned.add("Demonic Tutor");
        banned.add("Earthcraft");
        banned.add("Edric, Spymaster of Trest");
        banned.add("Fastbond");
        banned.add("Goblin Recruiter");
        banned.add("Grindstone"); // banned effective July 13, 2015
        banned.add("Hermit Druid");
        banned.add("Imperial Seal");
        banned.add("Library of Alexandria");
        banned.add("Karakas");
        banned.add("Mana Crypt");
        banned.add("Mana Drain");
        banned.add("Mana Vault");
        banned.add("metalworker");
        banned.add("Mind Twist");
        banned.add("Mishra's Workshop");
        banned.add("Mox Emerald");
        banned.add("Mox Jet");
        banned.add("Mox Pearl");
        banned.add("Mox Ruby");
        banned.add("Mox Sapphire");
        banned.add("Necropotence");
        banned.add("Shahrazad");
        banned.add("Skullclamp");
        banned.add("Sol Ring");
        banned.add("Strip Mine");
        banned.add("Survival of the Fittest");
        banned.add("Sword of Body and Mind");
        banned.add("Time Vault");
        banned.add("Time Walk");
        banned.add("Timetwister");
        banned.add("Tolarian Academy");
        banned.add("Umezawa's Jitte");
        banned.add("Vampiric Tutor");
        banned.add("Yawgmoth's Will");

        //Additionally, these Legendary creatures cannot be used as Commanders
        bannedCommander.add("Erayo, Soratami Ascendant");
        bannedCommander.add("Rofellos, Llanowar Emissary");
        bannedCommander.add("Derevi, Empyrical Tactician");
    }

    public TinyLeaders(String name) {
        super(name);
    }

    /**
     *
     * @param deck
     * @return - True if deck is valid
     */
    @Override
    public boolean validate(Deck deck) {
        boolean valid = true;

        if (deck.getCards().size() != 49) {
            invalid.put("Deck", "Must contain 49 cards: has " + deck.getCards().size() + " cards");
            valid = false;
        }

        Map<String, Integer> counts = new HashMap<>();
        counts.put(deck.getName(), 1); // add the commander to the counts, so it can't be in the deck or sideboard again
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                if (!basicLandNames.contains(entry.getKey()) && !anyNumberCardsAllowed.contains(entry.getKey())) {
                    invalid.put(entry.getKey(), "Too many: " + entry.getValue());
                    valid = false;
                }
            }
        }

        for (String bannedCard : banned) {
            if (counts.containsKey(bannedCard)) {
                invalid.put(bannedCard, "Banned");
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

            if (commander == null || commander.getManaCost().convertedManaCost() > 3) {
                if (commander == null) {
                    if (deck.getName() == null) {
                        invalid.put("Leader", "You have to save your deck with the leader card name entered to the DECK NAME field of the DECK EDITOR (top left) so that XMage knows your leader."
                                + "(You can use the \"Sultai\" for a UBG (3/3) default Commander or \"Glass\" for a colorless 3/3 default Commander.)");
                    } else {
                        invalid.put("Leader", "Leader [" + deck.getName() + "] not found. You have to enter the name of the leader card into the DECK NAME field of the DECK EDITOR (top left). Check your spelling "
                                + "(use the \"Sultai\" for a UBG (3/3) default Commander or \"Glass\" for a colorless (3/3) default Commander)");

                    }
                }
                if (commander != null && commander.getManaCost().convertedManaCost() > 3) {
                    invalid.put("Leader", "Commanders converted mana cost is greater than 3");
                }
                return false;
            }
            if ((commander.isCreature() && commander.isLegendary())
                    || (commander.isPlaneswalker() && commander.getAbilities().contains(CanBeYourCommanderAbility.getInstance()))) {
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
                    invalid.put("Commander", "Commander banned (" + commander.getName() + ')');
                    valid = false;
                }
            } else {
                invalid.put("Commander", "Commander invalide (" + commander.getName() + ')');
                valid = false;
            }
        } else {
            invalid.put("Commander", "Sideboard must contain only a maximum of 10 sideboard cards (the Tiny Leader name must be written to the deck name)");
            valid = false;
        }
        for (Card card : deck.getCards()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        for (Card card : deck.getSideboard()) {
            if (!isSetAllowed(card.getExpansionSetCode())) {
                if (!legalSets(card)) {
                    invalid.put(card.getName(), "Not allowed Set " + card.getExpansionSetCode());
                    valid = false;
                }
            }
        }
        return valid;
    }

    private boolean isCardFormatValid(Card card, Card commander, FilterMana color) {
        if (!cardHasValideColor(color, card)) {
            invalid.put(card.getName(), "Invalid color (" + commander.getName() + ')');
            return false;
        }

        //905.5b - Converted mana cost must be 3 or less
        if (card instanceof SplitCard) {
            if (((SplitCard) card).getLeftHalfCard().getManaCost().convertedManaCost() > 3) {
                invalid.put(card.getName(), "Invalid cost (" + ((SplitCard) card).getLeftHalfCard().getManaCost().convertedManaCost() + ')');
                return false;
            }
            if (((SplitCard) card).getRightHalfCard().getManaCost().convertedManaCost() > 3) {
                invalid.put(card.getName(), "Invalid cost (" + ((SplitCard) card).getRightHalfCard().getManaCost().convertedManaCost() + ')');
                return false;
            }
        } else if (card.getManaCost().convertedManaCost() > 3) {
            invalid.put(card.getName(), "Invalid cost (" + card.getManaCost().convertedManaCost() + ')');
            return false;
        }
        return true;
    }

    /**
     *
     * @param commander FilterMana object with Color Identity of Commander set
     * @param card Card to validate
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
