package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SourceRemainsInZoneCondition implements Condition {

    private final Zone zone;
    private int timesChangedZones = -1;

    public SourceRemainsInZoneCondition(Zone zone) {
        this.zone = zone;
        this.timesChangedZones = -1;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (timesChangedZones == -1) { // Only changed on first execution 
            timesChangedZones = game.getState().getZoneChangeCounter(source.getSourceId());
        }
        return (timesChangedZones == game.getState().getZoneChangeCounter(source.getSourceId())
                && zone.equals(game.getState().getZone(source.getSourceId())));
    }

    @Override
    public String toString() {
        return "for as long as {this} remains on the " + zone.toString();
    }
}
