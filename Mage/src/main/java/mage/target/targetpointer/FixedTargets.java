package mage.target.targetpointer;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Targets list with static ZCC
 *
 * @author LevelX2
 */
public class FixedTargets extends TargetPointerImpl {

    final ArrayList<MageObjectReference> targets = new ArrayList<>();

    public FixedTargets(List<Permanent> objects, Game game) {
        this(objects
                .stream()
                .map(o -> new MageObjectReference(o.getId(), game))
                .collect(Collectors.toList()));
    }

    public FixedTargets(Set<Card> objects, Game game) {
        this(objects
                .stream()
                .map(o -> new MageObjectReference(o.getId(), game))
                .collect(Collectors.toList()));
    }

    public FixedTargets(Cards objects, Game game) {
        this(objects.getCards(game)
                .stream()
                .map(o -> new MageObjectReference(o.getId(), game))
                .collect(Collectors.toList()));
    }

    public FixedTargets(Token token, Game game) {
        this(token.getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .collect(Collectors.toList()), game);
    }

    public FixedTargets(List<MageObjectReference> morList) {
        super();
        targets.addAll(morList);
        this.setInitialized(); // no need dynamic init
    }

    private FixedTargets(final FixedTargets pointer) {
        super(pointer);
        this.targets.addAll(pointer.targets);
    }

    @Override
    public void init(Game game, Ability source) {
        if (isInitialized()) {
            return;
        }

        // impossible use case
        throw new IllegalArgumentException("Wrong code usage: FixedTargets support only static ZCC, you can't get here");
    }

    @Override
    public List<UUID> getTargets(Game game, Ability source) {
        // check target not changed zone
        return targets
                .stream()
                .filter(mor -> mor.zoneCounterIsCurrent(game))
                .map(MageObjectReference::getSourceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public UUID getFirst(Game game, Ability source) {
        // check target not changed zone
        return targets
                .stream()
                .filter(mor -> mor.zoneCounterIsCurrent(game))
                .map(MageObjectReference::getSourceId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public FixedTargets copy() {
        return new FixedTargets(this);
    }

    @Override
    public Permanent getFirstTargetPermanentOrLKI(Game game, Ability source) {
        UUID targetId = null;
        int zoneChangeCounter = Integer.MIN_VALUE;
        if (!targets.isEmpty()) {
            MageObjectReference mor = targets.get(0);
            targetId = mor.getSourceId();
            zoneChangeCounter = mor.getZoneChangeCounter();
        }
        if (targetId != null) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null
                    && (zoneChangeCounter == Integer.MIN_VALUE || permanent.getZoneChangeCounter(game) == zoneChangeCounter)) {
                return permanent;
            }
            MageObject mageObject;
            if (zoneChangeCounter == Integer.MIN_VALUE) {
                mageObject = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
            } else {
                mageObject = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD, zoneChangeCounter);
            }
            if (mageObject instanceof Permanent) {
                return (Permanent) mageObject;
            }
        }
        return null;
    }
}
