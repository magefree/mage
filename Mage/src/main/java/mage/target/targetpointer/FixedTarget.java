package mage.target.targetpointer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class FixedTarget implements TargetPointer {

    private final UUID targetId;
    private int zoneChangeCounter;
    private boolean initialized;

    public FixedTarget(UUID target) {
        this.targetId = target;
        this.initialized = false;
    }

    public FixedTarget(Card card, Game game) {
        this.targetId = card.getId();
        this.zoneChangeCounter = card.getZoneChangeCounter(game);
        this.initialized = true;
    }

    public FixedTarget(Permanent permanent, Game game) {
        this.targetId = permanent.getId();
        this.zoneChangeCounter = permanent.getZoneChangeCounter(game);
        this.initialized = true;
    }

    /**
     * Use this if you already want to fix the target object to the known zone
     * now (otherwise the zone will be set if the ability triggers or not at
     * all) If not initialized, the object of the current zone then will be
     * used.
     *
     * @param targetId
     * @param zoneChangeCounter
     */
    public FixedTarget(UUID targetId, int zoneChangeCounter) {
        this.targetId = targetId;
        this.initialized = true;
        this.zoneChangeCounter = zoneChangeCounter;
    }

    /**
     * Use this to set the target to exactly the zone the target is currently in
     *
     * @param targetId
     * @param game
     */
    public FixedTarget(UUID targetId, Game game) {
        this.targetId = targetId;
        this.initialized = true;
        this.zoneChangeCounter = game.getState().getZoneChangeCounter(targetId);
    }

    public FixedTarget(final FixedTarget fixedTarget) {
        this.targetId = fixedTarget.targetId;
        this.zoneChangeCounter = fixedTarget.zoneChangeCounter;
        this.initialized = fixedTarget.initialized;
    }

    @Override
    public void init(Game game, Ability source) {
        if (!initialized) {
            initialized = true;
            this.zoneChangeCounter = game.getState().getZoneChangeCounter(targetId);
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        // check target not changed zone
        if (this.zoneChangeCounter > 0) { // will be zero if not defined in init
            Card card = game.getCard(targetId);
            if (card != null && card.getZoneChangeCounter(game) != this.zoneChangeCounter) {
                return Collections.emptyList(); // return empty
            }
        }

        ArrayList<UUID> list = new ArrayList<>(1);
        list.add(targetId);
        return list;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        // check target not changed zone
        if (this.zoneChangeCounter > 0) { // will be zero if not defined in init
            Card card = game.getCard(targetId);
            if (card != null && card.getZoneChangeCounter(game) != this.zoneChangeCounter) {
                return null;
            }
        }

        return targetId;
    }

    @Override
    public TargetPointer copy() {
        return new FixedTarget(this);
    }

    public UUID getTarget() {
        return targetId;
    }

    public int getZoneChangeCounter() {
        return zoneChangeCounter;
    }

    public Permanent getTargetedPermanentOrLKIBattlefield(Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
        if (permanent != null && permanent.getZoneChangeCounter(game) != zoneChangeCounter) {
            permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD, zoneChangeCounter);
        }
        return permanent;
    }

    @Override
    public FixedTarget getFixedTarget(Game game, Ability source) {
        init(game, source);
        return this;
    }

}
