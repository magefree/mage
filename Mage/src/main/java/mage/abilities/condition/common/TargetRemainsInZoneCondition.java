package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public class TargetRemainsInZoneCondition implements Condition {

    private final Zone zone;
    private final UUID targetId;

    private int timesChangedZones = -1;

    public TargetRemainsInZoneCondition(Zone zone, UUID targetId) {
        this.zone = zone;
        this.targetId = targetId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (timesChangedZones == -1) { // Only changed on first execution 
            timesChangedZones = game.getState().getZoneChangeCounter(targetId);
        }
        return (timesChangedZones == game.getState().getZoneChangeCounter(targetId)
                && zone.equals(game.getState().getZone(targetId)));
    }
}
