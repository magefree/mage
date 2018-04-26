package mage.target.targetpointer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class FirstTargetPointer implements TargetPointer {

    private Map<UUID, Integer> zoneChangeCounter = new HashMap<>();

    public static FirstTargetPointer getInstance() {
        return new FirstTargetPointer();
    }

    public FirstTargetPointer() {
    }

    public FirstTargetPointer(FirstTargetPointer firstTargetPointer) {
        this.zoneChangeCounter = new HashMap<>();
        for (Map.Entry<UUID, Integer> entry : firstTargetPointer.zoneChangeCounter.entrySet()) {
            this.zoneChangeCounter.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void init(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            for (UUID target : source.getTargets().get(0).getTargets()) {
                Card card = game.getCard(target);
                if (card != null) {
                    this.zoneChangeCounter.put(target, card.getZoneChangeCounter(game));
                }
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        ArrayList<UUID> target = new ArrayList<>();
        if (!source.getTargets().isEmpty()) {
            for (UUID targetId : source.getTargets().get(0).getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null && zoneChangeCounter.containsKey(targetId)
                        && card.getZoneChangeCounter(game) != zoneChangeCounter.get(targetId)) {
                    // but no longer if new permanent is already on the battlefield
                    Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
                    if (permanent == null || permanent.getZoneChangeCounter(game) != zoneChangeCounter.get(targetId)) {
                        continue;
                    }
                }
                target.add(targetId);
            }
        }
        return target;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        if (zoneChangeCounter.containsKey(targetId)) {
            Card card = game.getCard(targetId);
            if (card != null && zoneChangeCounter.containsKey(targetId)
                    && card.getZoneChangeCounter(game) != zoneChangeCounter.get(targetId)) {
                // because if dies trigger has to trigger as permanent has already moved zone, we have to check if target was on the battlefield immed. before
                // but no longer if new permanent is already on the battlefield
                Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
                if (permanent == null || permanent.getZoneChangeCounter(game) != zoneChangeCounter.get(targetId)) {
                    return null;
                }
            }
        }
        return targetId;
    }

    @Override
    public TargetPointer copy() {
        return new FirstTargetPointer(this);
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

}
