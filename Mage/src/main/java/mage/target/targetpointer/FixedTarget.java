package mage.target.targetpointer;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FixedTarget extends TargetPointerImpl {

    private final UUID targetId;
    private int zoneChangeCounter = 0;

    /**
     * Dynamic ZCC (not recommended)
     * <p>
     * Use this best only to target to a player or spells on the stack. Try to
     * avoid this method to set the target to a specific card or permanent if
     * possible. Because the zoneChangeCounter is not set immediately, it can be
     * undefined to which object you refer to at the end. Best is to set the
     * target for cards or permanents by using the methods with the card or
     * permanent object or also setting the zoneChangeCounter directly.
     *
     * @param target
     */
    public FixedTarget(UUID target) {
        super();
        this.targetId = target;
    }

    public FixedTarget(MageObjectReference mor) {
        this(mor.getSourceId(), mor.getZoneChangeCounter());
    }

    /**
     * Static ZCC
     * <p>
     * Target counter is immediately initialised with current zoneChangeCounter
     * value from the GameState Sets fixed the currect zoneChangeCounter
     *
     * @param card used to get the objectId
     * @param game
     */
    public FixedTarget(Card card, Game game) {
        super();
        this.targetId = card.getId();
        this.zoneChangeCounter = card.getZoneChangeCounter(game);

        this.setInitialized(); // no need dynamic init
    }

    /**
     * Static ZCC
     * <p>
     * Target counter is immediately initialized with current zoneChangeCounter
     * value from the given permanent
     *
     * @param permanent
     * @param game
     */
    public FixedTarget(Permanent permanent, Game game) {
        this(permanent.getId(), game);
    }

    /**
     * Static ZCC
     * <p>
     * Use this if you already want to fix the target object to the known zone
     * now (otherwise the zone will be set if the ability triggers or not at
     * all) If not initialized, the object of the current zone then will be
     * used.
     *
     * @param targetId
     * @param zoneChangeCounter
     */
    public FixedTarget(UUID targetId, int zoneChangeCounter) {
        super();
        this.targetId = targetId;
        this.zoneChangeCounter = zoneChangeCounter;

        this.setInitialized(); // no need dynamic init
    }

    /**
     * Static ZCC
     * <p>
     * Use this to set the target to exactly the zone the target is currently in
     *
     * @param targetId
     * @param game
     */
    public FixedTarget(UUID targetId, Game game) {
        super();
        this.targetId = targetId;
        this.zoneChangeCounter = game.getState().getZoneChangeCounter(targetId);

        this.setInitialized(); // no need dynamic init
    }

    protected FixedTarget(final FixedTarget targetPointer) {
        super(targetPointer);

        this.targetId = targetPointer.targetId;
        this.zoneChangeCounter = targetPointer.zoneChangeCounter;
    }

    @Override
    public void init(Game game, Ability source) {
        if (isInitialized()) {
            return;
        }
        setInitialized();

        this.zoneChangeCounter = game.getState().getZoneChangeCounter(targetId);
    }

    /**
     * This returns a list of the targetIds (but only if the targets are still
     * have the same zoneChangeCounter). So if the target has changed zone
     * meanwhile there is no id returned for this target and the list is empty.
     *
     * @param game
     * @param source
     * @return
     */
    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        // check target not changed zone
        if (this.zoneChangeCounter > 0) { // will be zero if not defined in init
            Card card = game.getCard(targetId);
            if (card != null && card.getZoneChangeCounter(game) != this.zoneChangeCounter) {
                return Collections.emptyList(); // return empty
            }
        }

        List<UUID> list = new ArrayList<>();
        if (targetId != null) {
            list.add(targetId);
        }
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
    public FixedTarget copy() {
        return new FixedTarget(this);
    }

    public UUID getTarget() {
        return targetId;
    }

    public int getZoneChangeCounter() {
        return zoneChangeCounter;
    }

    @Override
    public Permanent getFirstTargetPermanentOrLKI(Game game, Ability source) {
        init(game, source);
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null && permanent.getZoneChangeCounter(game) == zoneChangeCounter) {
            return permanent;
        }
        MageObject mageObject = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD, zoneChangeCounter);
        if (mageObject instanceof Permanent) {
            return (Permanent) mageObject;
        }
        return null;
    }
}
