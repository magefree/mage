
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.cards.SplitCard;
import mage.constants.SpellAbilityType;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author North
 */
public class NamePredicate implements Predicate<MageObject> {

    private final String name;

    public NamePredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        // If a player names a card, the player may name either half of a split card, but not both. 
        // A split card has the chosen name if one of its two names matches the chosen name.
        if (input instanceof SplitCard) {
            return name.equals(((SplitCard)input).getLeftHalfCard().getName()) || name.equals(((SplitCard)input).getRightHalfCard().getName());
        } else if (input instanceof Spell && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED){
            SplitCard card = (SplitCard) ((Spell)input).getCard();
            return name.equals(card.getLeftHalfCard().getName()) || name.equals(card.getRightHalfCard().getName());
        } else {
            if (name.contains(" // ")) {
                String leftName = name.substring(0, name.indexOf(" // "));
                String rightName = name.substring(name.indexOf(" // ") + 4, name.length());
                return leftName.equals(input.getName()) || rightName.equals(input.getName());
            } else {
                return name.equals(input.getName());
            }
        }
    }

    @Override
    public String toString() {
        return "Name(" + name + ')';
    }
}
