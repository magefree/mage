package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

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

    @Override
    public boolean apply(MageObject input, Game game) {
        // If a player names a card, the player may name either half of a split card, but not both.
        // A split card has the chosen name if one of its two names matches the chosen name.
        // This is NOT the same for double faced cards, where only the front side matches

        // Test of Talents ruling:
        // If the back face of a modal double-faced card is countered, you will not be able to exile any cards,
        // including the one that you countered, because those cards have only their front-face characteristics
        // (including name) in the graveyard, hand, and library. (2021-04-16)
        return name != null && input.hasName(name);
    }

    @Override
    public String toString() {
        return "Name (" + name + ')';
    }
}
