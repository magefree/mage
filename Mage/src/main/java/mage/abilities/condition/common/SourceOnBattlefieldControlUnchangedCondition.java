package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.LostControlWatcher;

/**
 * This condition checks if ever since first call of the apply method the
 * controller of the source has changed
 *
 * Monitoring the LOST_CONTROL event has the advantage that also all layered
 * effects can correctly check for controller change because comparing old and
 * new controller during their apply time does not take into account layered
 * change control effects that will be applied later.
 *
 * This condition needs the LostControlWatcher, so be sure to add it to the card
 * that uses the condition.
 *
 * @author LevelX2
 */
public class SourceOnBattlefieldControlUnchangedCondition implements Condition {

    private Long checkingSince;
    private int startingZoneChangeCounter;

    @Override
    public boolean apply(Game game, Ability source) {
        if (checkingSince == null) {
            checkingSince = System.currentTimeMillis() - 1;
            startingZoneChangeCounter = game.getState().getZoneChangeCounter(source.getSourceId());
        }
        if (game.getState().getZoneChangeCounter(source.getSourceId()) > startingZoneChangeCounter) {
            return false;
        }
        LostControlWatcher watcher = game.getState().getWatcher(LostControlWatcher.class);
        if (watcher != null) {
            return checkingSince > watcher.getOrderOfLastLostControl(source.getSourceId());
        }
        throw new UnsupportedOperationException("LostControlWatcher not found!");
    }

}
