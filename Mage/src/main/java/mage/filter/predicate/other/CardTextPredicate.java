
package mage.filter.predicate.other;

import java.util.HashMap;
import java.util.Locale;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
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
            boolean found = !seenCards.keySet().contains(input.getName());
            seenCards.put(input.getName(), true);
            return found;
        }

        // first check in card name
        if (inNames && input.getName().toLowerCase(Locale.ENGLISH).contains(text.toLowerCase(Locale.ENGLISH))) {
            if (isUnique && seenCards.keySet().contains(input.getName())) {
                return false;
            }
            if (isUnique) {
                seenCards.put(input.getName(), true);
            }
            return true;
        }

        //separate by spaces
        String[] tokens = text.toLowerCase(Locale.ENGLISH).split(" ");
        for (String token : tokens) {
            boolean found = false;
            if (!token.isEmpty()) {
                // then try to find in rules
                if (inRules) {
                    if (input.isSplitCard()) {
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
                    for (SuperType superType : input.getSuperType()) {
                        if (superType.toString().equalsIgnoreCase(token)) {
                            found = true;
                            break;
                        }
                    }
                }
            }

            if (found && isUnique && seenCards.keySet().contains(input.getName())) {
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
