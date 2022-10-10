package mage.target.targetpointer;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Targets;

import java.util.*;

/**
 * Class to create the Nth target pointer classes (FirstTargetPointer, SecondTargetPointer, etc._
 */
public abstract class NthTargetPointer extends TargetPointerImpl {

    private static final Map<UUID, Integer> emptyZoneChangeCounter = Collections.unmodifiableMap(new HashMap<>(0));
    private static final List<UUID> emptyTargets = Collections.unmodifiableList(new ArrayList<>(0));

    private Map<UUID, Integer> zoneChangeCounter;
    private int targetNumber;

    public NthTargetPointer(int targetNumber) {
        super();
        this.targetNumber = targetNumber;
    }

    public NthTargetPointer(final  NthTargetPointer nthTargetPointer) {
        super(nthTargetPointer);
        this.targetNumber = nthTargetPointer.targetNumber;

        if (nthTargetPointer.zoneChangeCounter != null) {
            this.zoneChangeCounter = new HashMap<>(nthTargetPointer.zoneChangeCounter.size());
            for (Map.Entry<UUID, Integer> entry : nthTargetPointer.zoneChangeCounter.entrySet()) {
                addToZoneChangeCounter(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void init(Game game, Ability source) {
        if (source.getTargets().size() < targetNumber) {
            return;
        }

        for (UUID target : source.getTargets().get(targetIndex()).getTargets()) {
            Card card = game.getCard(target);
            if (card != null) {
                addToZoneChangeCounter(target, card.getZoneChangeCounter(game));
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        if (source.getTargets().size() < targetNumber) {
            return emptyTargets;
        }

        List<UUID> targetIds = source.getTargets().get(targetIndex()).getTargets();
        List<UUID> finalTargetIds = new ArrayList<>(targetIds.size());

        for (UUID targetId : targetIds) {
            Card card = game.getCard(targetId);
            if (card != null
                    && getZoneChangeCounter().containsKey(targetId)
            && card.getZoneChangeCounter(game) != getZoneChangeCounter().get(targetId)) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
                if (permanent == null || permanent.getZoneChangeCounter(game) != getZoneChangeCounter().get(targetId)) {
                    continue;
                }
            }

            finalTargetIds.add(targetId);
        }
        return finalTargetIds;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        if (source.getTargets().size() < targetNumber) {
            return null;
        }

        UUID targetId = source.getTargets().get(targetIndex()).getFirstTarget();
        if (getZoneChangeCounter().containsKey(targetId)) {
            Card card = game.getCard(targetId);
            if (card != null && getZoneChangeCounter().containsKey(targetId)
                    && card.getZoneChangeCounter(game) != getZoneChangeCounter().get(targetId)) {
                return null;
            }
        }
        return targetId;

    }

    @Override
    public FixedTarget getFixedTarget(Game game, Ability source) {
        this.init(game, source);
        UUID firstId = getFirst(game, source);
        if (firstId != null) {
            return new FixedTarget(firstId, game.getState().getZoneChangeCounter(firstId));
        }

        return null;
    }

    @Override
    public Permanent getFirstTargetPermanentOrLKI(Game game, Ability source) {
        if (source.getTargets().size() < targetNumber) {
            return null;
        }

        Permanent permanent;
        UUID targetId = source.getTargets().get(targetIndex()).getFirstTarget();

        if (getZoneChangeCounter().containsKey(targetId)) {
            permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.getZoneChangeCounter(game) == getZoneChangeCounter().get(targetId)) {
                return permanent;
            }
            MageObject mageObject = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD, getZoneChangeCounter().get(targetId));
            if (mageObject instanceof Permanent) {
                return (Permanent) mageObject;
            }

        } else {
            permanent = game.getPermanent(targetId);
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            }
        }
        return permanent;
    }

    @Override
    public String describeTargets(Targets targets, String defaultDescription) {
        return targets.size() < targetNumber ? defaultDescription : targets.get(targetIndex()).getDescription();
    }

    @Override
    public boolean isPlural(Targets targets) {
        return targets.size() > targetIndex() && targets.get(targetIndex()).getMaxNumberOfTargets() > 1;
    }

    private int targetIndex() {
        return targetNumber - 1;
    }

    private Map<UUID, Integer> getZoneChangeCounter() {
        return zoneChangeCounter != null ? zoneChangeCounter : emptyZoneChangeCounter;
    }

    private void addToZoneChangeCounter(UUID key, Integer value) {
        if (zoneChangeCounter == null) {
            zoneChangeCounter = new HashMap<>();
        }
        getZoneChangeCounter().put(key, value);
    }
}
