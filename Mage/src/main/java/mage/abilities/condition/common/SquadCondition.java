package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.SquadAbility;
import mage.cards.Card;
import mage.game.Game;

/**
 * Describes condition when spell was kicked.
 *
 * @author notgreat
 */
public enum SquadCondition implements Condition {
    instance;
    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject instanceof Card) {
            for (Ability ability : ((Card) sourceObject).getAbilities(game)) {
                if (ability instanceof SquadAbility) {
                    if (((SquadAbility) ability).isPaid(game, source)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this}'s squad cost was paid";
    }
}
