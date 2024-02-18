package mage.target.targetpointer;

import mage.MageObjectReference;
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

    private static final List<UUID> emptyTargets = Collections.unmodifiableList(new ArrayList<>(0));

    // TODO: rework to list of MageObjectReference instead zcc
    private final Map<UUID, Integer> zoneChangeCounter = new HashMap<>();
    private final int targetIndex; // zero-based target numbers (1 -> 0, 2 -> 1, 3 -> 2, etc)

    public NthTargetPointer(int targetNumber) {
        super();
        this.targetIndex = targetNumber - 1;
    }

    protected NthTargetPointer(final NthTargetPointer nthTargetPointer) {
        super(nthTargetPointer);
        this.targetIndex = nthTargetPointer.targetIndex;
        this.zoneChangeCounter.putAll(nthTargetPointer.zoneChangeCounter);
    }

    @Override
    public void init(Game game, Ability source) {
        if (isInitialized()) {
            return;
        }
        this.setInitialized();

        if (source.getTargets().size() <= this.targetIndex) {
            wrongTargetsUsage(source);
            return;
        }

        for (UUID target : source.getTargets().get(this.targetIndex).getTargets()) {
            Card card = game.getCard(target);
            if (card != null) {
                this.zoneChangeCounter.put(target, card.getZoneChangeCounter(game));
            }
        }
    }

    private void wrongTargetsUsage(Ability source) {
        if (this.targetIndex > 0) {
            // first target pointer is default, so must be ignored
            throw new IllegalStateException("Wrong code usage: source ability miss targets setup for target pointer - "
                    + this.getClass().getSimpleName() + " - " + source.getClass().getSimpleName() + " - " + source);
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        // can be used before effect's init (example: checking spell targets on stack before resolve like HeroicAbility)

        if (source.getTargets().size() <= this.targetIndex) {
            wrongTargetsUsage(source);
            return emptyTargets;
        }

        List<UUID> res = new ArrayList<>();
        for (UUID targetId : source.getTargets().get(this.targetIndex).getTargets()) {
            if (!isOutdatedTarget(game, targetId)) {
                res.add(targetId);
            }
        }
        return res;
    }

    private boolean isOutdatedTarget(Game game, UUID targetId) {
        int needZcc = this.zoneChangeCounter.getOrDefault(targetId, 0);
        if (needZcc == 0) {
            // any zcc (target not init yet here)
            return false;
        }

        // card
        Card card = game.getCard(targetId);
        if (card != null && card.getZoneChangeCounter(game) == needZcc) {
            return false;
        }

        // permanent
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
        if (permanent != null && permanent.getZoneChangeCounter(game) == needZcc) {
            return false;
        }

        // TODO: if no bug reports with die triggers and new code then remove it, 2024-02-18
        //  if you catch bugs then add code like if permanent.getZoneChangeCounter(game) == needZcc + 1 then return false
        // old comments:
        // Because if dies trigger has to trigger as permanent has already moved zone, we have to check if target
        // was on the battlefield immed. before, but no longer if new permanent is already on the battlefield

        // outdated
        return true;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        if (source.getTargets().size() <= this.targetIndex) {
            wrongTargetsUsage(source);
            return null;
        }

        UUID targetId = source.getTargets().get(this.targetIndex).getFirstTarget();
        if (isOutdatedTarget(game, targetId)) {
            return null;
        }

        return targetId;
    }

    @Override
    public Permanent getFirstTargetPermanentOrLKI(Game game, Ability source) {
        if (source.getTargets().size() < this.targetIndex) {
            wrongTargetsUsage(source);
            return null;
        }
        UUID targetId = source.getTargets().get(this.targetIndex).getFirstTarget();

        if (this.zoneChangeCounter.containsKey(targetId)) {
            // need static zcc
            MageObjectReference needRef = new MageObjectReference(targetId, this.zoneChangeCounter.getOrDefault(targetId, 0), game);
            return game.getPermanentOrLKIBattlefield(needRef);
        } else {
            // need any zcc
            // TODO: must research, is it used at all?! Init code must fill all static zcc data before go here
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            }
            return permanent;
        }
    }

    @Override
    public String describeTargets(Targets targets, String defaultDescription) {
        if (targets.size() <= this.targetIndex) {
            // TODO: need research, is it used for non setup targets ?!
            return defaultDescription;
        } else {
            return targets.get(this.targetIndex).getDescription();
        }
    }

    @Override
    public boolean isPlural(Targets targets) {
        return targets.size() > this.targetIndex && targets.get(this.targetIndex).getMaxNumberOfTargets() > 1;
    }
}
