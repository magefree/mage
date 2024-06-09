package mage.filter.predicate.card;

import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.SplitCard;
import mage.cards.mock.MockCard;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

import java.util.HashMap;
import java.util.Locale;

/**
 * Special predicate to search cards in deck editor
 *
 * @author North
 */
public class CardTextPredicate implements Predicate<Card> {

    private final String text;
    private final boolean inNames;
    private final boolean inTypes;
    private final boolean inRules;
    private final boolean isUnique;
    private HashMap<String, Boolean> seenCards = null;

    public CardTextPredicate(String text, boolean inNames, boolean inTypes, boolean inRules, boolean isUnique) {
        this.text = text;
        this.inNames = inNames;
        this.inTypes = inTypes;
        this.inRules = inRules;
        this.isUnique = isUnique;
        seenCards = new HashMap<>();
    }

    @Override
    public boolean apply(Card input, Game game) {
        if (text.isEmpty() && !isUnique) {
            return true;
        }

        if (text.isEmpty() && isUnique) {
            boolean found = !seenCards.containsKey(input.getName());
            seenCards.put(input.getName(), true);
            return found;
        }

        // first check in card name
        if (inNames) {
            String fullName = input.getName();
            if (input instanceof MockCard) {
                fullName = ((MockCard) input).getFullName(true);
            } else if (input instanceof ModalDoubleFacedCard) {
                fullName = input.getName() + MockCard.MODAL_DOUBLE_FACES_NAME_SEPARATOR + ((ModalDoubleFacedCard) input).getRightHalfCard().getName();
            } else if (input instanceof AdventureCard) {
                fullName = input.getName() + MockCard.ADVENTURE_NAME_SEPARATOR + ((AdventureCard) input).getSpellCard().getName();
            }

            if (fullName.toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.ENGLISH))) {
                if (isUnique && seenCards.containsKey(input.getName())) {
                    return false;
                }
                if (isUnique) {
                    seenCards.put(input.getName(), true);
                }
                return true;
            }
        }

        // separate by spaces
        String[] tokens = text.toLowerCase(Locale.ENGLISH).split(" ");
        for (String token : tokens) {
            boolean found = false;
            if (!token.isEmpty()) {
                // then try to find in rules
                if (inRules) {
                    if (input instanceof SplitCard) {
                        for (String rule : ((SplitCard) input).getLeftHalfCard().getRules(game)) {
                            if (rule.toLowerCase(Locale.ENGLISH).contains(token)) {
                                found = true;
                                break;
                            }
                        }
                        for (String rule : ((SplitCard) input).getRightHalfCard().getRules(game)) {
                            if (rule.toLowerCase(Locale.ENGLISH).contains(token)) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if (input instanceof ModalDoubleFacedCard) {
                        for (String rule : ((ModalDoubleFacedCard) input).getLeftHalfCard().getRules(game)) {
                            if (rule.toLowerCase(Locale.ENGLISH).contains(token)) {
                                found = true;
                                break;
                            }
                        }
                        for (String rule : ((ModalDoubleFacedCard) input).getRightHalfCard().getRules(game)) {
                            if (rule.toLowerCase(Locale.ENGLISH).contains(token)) {
                                found = true;
                                break;
                            }
                        }
                    }

                    if (input instanceof AdventureCard) {
                        for (String rule : ((AdventureCard) input).getSpellCard().getRules(game)) {
                            if (rule.toLowerCase(Locale.ENGLISH).contains(token)) {
                                found = true;
                                break;
                            }
                        }
                    }

                    for (String rule : input.getRules(game)) {
                        if (rule.toLowerCase(Locale.ENGLISH).contains(token)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (inTypes) {
                    for (SubType subType : input.getSubtype(game)) {
                        if (subType.toString().equalsIgnoreCase(token)) {
                            found = true;
                            break;
                        }
                    }
                    for (SuperType superType : input.getSuperType(game)) {
                        if (superType.toString().equalsIgnoreCase(token)) {
                            found = true;
                            break;
                        }
                    }
                }
            }

            if (found && isUnique && seenCards.containsKey(input.getName())) {
                found = false;
            }
            if (!found) {
                return false;
            }
        }

        if (isUnique) {
            seenCards.put(input.getName(), true);
        }
        return true;
    }

    @Override
    public String toString() {
        return "CardText(" + text + ')';
    }
}
