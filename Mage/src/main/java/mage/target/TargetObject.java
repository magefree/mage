package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetObject extends TargetImpl {

    protected TargetObject() {
    }

    public TargetObject(Zone zone) {
        this(1, 1, zone, false);
    }

    public TargetObject(int numTargets, Zone zone) {
        this(numTargets, numTargets, zone, false);
    }

    protected TargetObject(final TargetObject target) {
        super(target);
    }

    public TargetObject(int minNumTargets, int maxNumTargets, Zone zone, boolean notTarget) {
        this.minNumberOfTargets = minNumTargets;
        this.maxNumberOfTargets = maxNumTargets;
        this.zone = zone;
        this.targetName = "card";
        this.notTarget = notTarget;
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder();
        for (UUID targetId : getTargets()) {
            MageObject object = game.getObject(targetId);
            if (object != null) {
                sb.append(object.getLogName()).append(' ');
            }
        }
        return sb.toString().trim();
    }

    /**
     * Warning, don't use with non card objects here like commanders/emblems/etc. If you want it then
     * override canTarget in your own target.
     *
     * @param id
     * @param game
     * @return
     */
    @Override
    public boolean canTarget(UUID id, Game game) {
        MageObject object = game.getObject(id);
        return object != null
                && zone != null && zone.match(game.getState().getZone(id))
                && getFilter() != null && getFilter().match(object, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        return canTarget(id, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

}
