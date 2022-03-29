package mage.target.targetpointer;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public abstract class NonFixedTargetPointer extends TargetPointerImpl {

    protected final List<MageObjectReference> targets = new ArrayList<>();

    protected NonFixedTargetPointer() {
        super();
    }

    protected NonFixedTargetPointer(final NonFixedTargetPointer targetPointer) {
        super(targetPointer);
        this.targets.addAll(targetPointer.targets);
    }

    protected abstract List<UUID> getTargetIds(Game game, Ability source);

    @Override
    public void init(Game game, Ability source) {
        for (UUID target : getTargetIds(game, source)) {
            this.targets.add(new MageObjectReference(target, game));
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        return targets
                .stream()
                .filter(mor -> mor.zoneCounterIsCurrent(game))
                .map(MageObjectReference::getSourceId)
                .collect(Collectors.toList());
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        return targets
                .stream()
                .findFirst()
                .filter(mor -> mor.zoneCounterIsCurrent(game))
                .map(MageObjectReference::getSourceId)
                .orElse(null);
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
        if (!targets.isEmpty()) {
            return targets.get(0).getPermanent(game);
        }
        return null;
    }
}
