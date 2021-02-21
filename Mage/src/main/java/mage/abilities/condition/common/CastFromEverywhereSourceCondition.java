package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public enum CastFromEverywhereSourceCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        int zccDiff = 0;
        if (permanent == null) {
            permanent = game.getPermanentOrLKIBattlefield(source.getSourceId()); // can be alredy again removed from battlefield so also check LKI
            zccDiff = -1;
        }
        if (permanent != null) {
            // check that the spell is still in the LKI
            Spell spell = game.getStack().getSpell(source.getSourceId());
            if (spell == null || spell.getZoneChangeCounter(game) != permanent.getZoneChangeCounter(game) + zccDiff) {
                if (game.getLastKnownInformation(source.getSourceId(), Zone.STACK, permanent.getZoneChangeCounter(game) + zccDiff) == null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "you cast it";
    }

}
