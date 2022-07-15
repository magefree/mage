package mage.target.targetpointer;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetImpl;

import java.util.*;
import java.util.stream.Collectors;

public class SecondTargetPointer extends TargetPointerImpl {

    private Map<UUID, Integer> zoneChangeCounter = new HashMap<>();

    public static SecondTargetPointer getInstance() {
        return new SecondTargetPointer();
    }

    public SecondTargetPointer() {
        super();
    }

    public SecondTargetPointer(final SecondTargetPointer targetPointer) {
        super(targetPointer);

        this.zoneChangeCounter = new HashMap<>();
        for (Map.Entry<UUID, Integer> entry : targetPointer.zoneChangeCounter.entrySet()) {
            this.zoneChangeCounter.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void init(Game game, Ability source) {
        if (source.getTargets().size() > 1) {
            for (UUID target : source.getTargets().get(1).getTargets()) {
                Card card = game.getCard(target);
                if (card != null) {
                    this.zoneChangeCounter.put(target, card.getZoneChangeCounter(game));
                }
            }
        }
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        List<UUID> target = new ArrayList<>();
        if (source.getTargets().size() > 1) {
            for (UUID targetId : source.getTargets().get(1).getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null && zoneChangeCounter.containsKey(targetId)
                        && card.getZoneChangeCounter(game) != zoneChangeCounter.get(targetId)) {
                    continue;
                }
                target.add(targetId);
            }
        }
        return target;
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        if (source.getTargets().size() > 1) {
            UUID targetId = source.getTargets().get(1).getFirstTarget();
            if (zoneChangeCounter.containsKey(targetId)) {
                Card card = game.getCard(targetId);
                if (card != null && zoneChangeCounter.containsKey(targetId)
                        && card.getZoneChangeCounter(game) != zoneChangeCounter.get(targetId)) {
                    return null;
                }
            }
            return targetId;
        }
        return null;
    }

    @Override
    public SecondTargetPointer copy() {
        return new SecondTargetPointer(this);
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
        if (source.getTargets().size() > 1) {
            Permanent permanent;
            UUID targetId = source.getTargets().get(1).getFirstTarget();
            if (zoneChangeCounter.containsKey(targetId)) {
                permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.getZoneChangeCounter(game) == zoneChangeCounter.get(targetId)) {
                    return permanent;
                }
                MageObject mageObject = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD, zoneChangeCounter.get(targetId));
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
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        SecondTargetPointer that = (SecondTargetPointer) obj;

        List<UUID> thisZCCIds = this.zoneChangeCounter.keySet().stream().sorted().collect(Collectors.toList());
        List<UUID> thatZCCIds = that.zoneChangeCounter.keySet().stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < thisZCCIds.size(); i++) {
            UUID thisId = thisZCCIds.get(i);
            UUID thatId = thatZCCIds.get(i);
            if (!Objects.equals(thisId, thatId)
                    || !Objects.equals(this.zoneChangeCounter.get(thisId), that.zoneChangeCounter.get(thatId))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equivalent(Object obj, Game game) {
        if (!super.equivalent(obj, game)) {
            return false;
        }
        SecondTargetPointer that = (SecondTargetPointer) obj;

        return TargetImpl.mapsEquivalent(this.zoneChangeCounter, that.zoneChangeCounter, game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), zoneChangeCounter);
    }
}
