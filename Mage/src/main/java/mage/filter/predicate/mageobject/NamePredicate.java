package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.CardWithHalves;
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

    public NamePredicate(String name) {
        this(name, false);
    }

    public NamePredicate(String name, Boolean ignoreMtgRuleForEmptyNames) {
        this.name = name;
        this.ignoreMtgRuleForEmptyNames = ignoreMtgRuleForEmptyNames;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        if (name == null) {
            return false;
        }
        // If a player names a card, the player may name either half of a split card, but not both. 
        // A split card has the chosen name if one of its two names matches the chosen name.
        // Same for modal double faces cards
        if (input instanceof CardWithHalves) {
            return CardUtil.haveSameNames(name, ((CardWithHalves) input).getLeftHalfCard().getName(), this.ignoreMtgRuleForEmptyNames) ||
                    CardUtil.haveSameNames(name, ((CardWithHalves) input).getRightHalfCard().getName(), this.ignoreMtgRuleForEmptyNames) ||
                    CardUtil.haveSameNames(name, input.getName(), this.ignoreMtgRuleForEmptyNames);
        } else if (input instanceof Spell && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            SplitCard card = (SplitCard) ((Spell) input).getCard();
            return CardUtil.haveSameNames(name, card.getLeftHalfCard().getName(), this.ignoreMtgRuleForEmptyNames) ||
                    CardUtil.haveSameNames(name, card.getRightHalfCard().getName(), this.ignoreMtgRuleForEmptyNames) ||
                    CardUtil.haveSameNames(name, card.getName(), this.ignoreMtgRuleForEmptyNames);
        } else if (input instanceof Spell && ((Spell) input).isFaceDown(game)) {
            // face down spells don't have names, so it's not equal, see https://github.com/magefree/mage/issues/6569
            return false;
        } else {
            if (name.contains(" // ")) {
                String leftName = name.substring(0, name.indexOf(" // "));
                String rightName = name.substring(name.indexOf(" // ") + 4);
                return CardUtil.haveSameNames(leftName, input.getName(), this.ignoreMtgRuleForEmptyNames) ||
                        CardUtil.haveSameNames(rightName, input.getName(), this.ignoreMtgRuleForEmptyNames);
            } else {
                return CardUtil.haveSameNames(name, input.getName(), this.ignoreMtgRuleForEmptyNames);
            }
        }
    }

    @Override
    public String toString() {
        return "Name (" + name + ')';
    }
}
