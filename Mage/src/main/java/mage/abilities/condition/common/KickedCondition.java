

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.game.Game;


/**
 * Describes condition when spell was kicked.
 *
 * @author LevelX2
 */
public enum KickedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            for (Ability ability: card.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    if(((KickerAbility) ability).isKicked(game, source, "")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} was kicked";
    }

}
