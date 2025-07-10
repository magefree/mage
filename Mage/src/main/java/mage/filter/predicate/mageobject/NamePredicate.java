package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.SplitCard;
import mage.constants.SpellAbilityType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 * @author North
 */
public class NamePredicate implements Predicate<MageObject> {

    private final String name;
    private final Boolean ignoreMtgRuleForEmptyNames; // NamePredicate uses at test and checks, it's must ignore that rules (empty names is not equals in mtg)

    /**
     * For cards/effects, must have a name to search
     *
     * @param name empty name for non-selected target/choice, e.g. return nothing
     */
    public NamePredicate(String name) {
        this(name, false);
    }

    /**
     * For tests only, can find face down permanents too
     *
     * @param name
     * @param ignoreMtgRuleForEmptyNames
     */
    public NamePredicate(String name, Boolean ignoreMtgRuleForEmptyNames) {
        this.name = name;
        this.ignoreMtgRuleForEmptyNames = ignoreMtgRuleForEmptyNames;
    }

    // Determines the names to search for (from the predicate's 'name' field)
    private String[] getSearchNames(String nameString) {
        // According to MTG rules, if a player "names a card", and that card is a split card,
        // they can name either half but not both
        if (nameString == null) {
            return new String[]{};
        }
        if (nameString.contains(" // ")) {
            // If the predicate itself is initialized with a split name (e.g., "Other // Dangerous"),
            // we should treat it as a single specific name to match against.
            // It's not about matching one of its halves, but matching that exact full name.
            return new String[] { nameString };
        } else {
            // If the predicate is initialized with a single name (e.g., "Armed"),
            // it means we are looking for a card that has this name, or a split card with this half.
            return new String[] { nameString };
        }
    }

    // Determines the names available on the target MageObject for comparison
    private String[] getTargetNames(MageObject input, Game game) {
        if (input instanceof SplitCard) {
            SplitCard splitCard = (SplitCard) input;
            // A split card has its individual half names and its full name for matching purposes.
            return new String[] {
                    splitCard.getLeftHalfCard().getName(),
                    splitCard.getRightHalfCard().getName(),
                    splitCard.getName() // The full name (e.g., "Armed // Dangerous")
            };
        } else if (input instanceof Spell
                && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            SplitCard card = (SplitCard) ((Spell) input).getCard();
            return new String[] {
                    card.getLeftHalfCard().getName(),
                    card.getRightHalfCard().getName(),
                    card.getName()
            };
        } else if (input instanceof Spell && ((Spell) input).isFaceDown(game)) {
            // face down spells don't have names, so it's not equal
            return new String[]{}; // Return empty array as it has no name
        } else {
            // For regular cards, just use their single name
            return new String[] { input.getName() };
        }
    }


    @Override
    public boolean apply(MageObject input, Game game) {
        if (name == null) {
            return false;
        }

        String[] searchNames = getSearchNames(this.name);
        String[] targetNames = getTargetNames(input, game); // Pass game for isFaceDown check

        return matchesAnyName(searchNames, targetNames);
    }


    private boolean matchesAnyName(String[] searchNames, String[] targetNames) {
        for (String searchName : searchNames) {
            for (String targetName : targetNames) {
                if (CardUtil.haveSameNames(searchName, targetName, this.ignoreMtgRuleForEmptyNames)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Name (" + name + ')';
    }
}
