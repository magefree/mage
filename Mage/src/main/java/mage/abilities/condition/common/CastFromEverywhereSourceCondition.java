package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
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
            permanent = game.getPermanentOrLKIBattlefield(source.getSourceId()); // can be already again removed from battlefield so also check LKI
            zccDiff = -1;
        }
        if (permanent != null) {
            Spell spell = game.getSpellOrLKIStack(permanent);
            if (spell == null || spell.getZoneChangeCounter(game) != permanent.getZoneChangeCounter(game) + zccDiff) {
                return false;
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
