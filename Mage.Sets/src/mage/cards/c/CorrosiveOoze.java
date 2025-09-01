package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rscoates, LevelX2, xenohedron
 */
public final class CorrosiveOoze extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("equipped creature");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public CorrosiveOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.OOZE);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Corrosive Ooze blocks or becomes blocked by an equipped creature, destroy all Equipment attached to that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new CorrosiveOozeEffect())
                .setTriggerPhrase(""), true);
        this.addAbility(new BlocksOrBlockedByCreatureSourceTriggeredAbility(effect, filter), new CorrosiveOozeCombatWatcher());
    }

    private CorrosiveOoze(final CorrosiveOoze card) {
        super(card);
    }

    @Override
    public CorrosiveOoze copy() {
        return new CorrosiveOoze(this);
    }
}

class CorrosiveOozeEffect extends OneShotEffect {

    CorrosiveOozeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all Equipment attached to that creature at end of combat";
    }

    private CorrosiveOozeEffect(final CorrosiveOozeEffect effect) {
        super(effect);
    }

    @Override
    public CorrosiveOozeEffect copy() {
        return new CorrosiveOozeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CorrosiveOozeCombatWatcher watcher = game.getState().getWatcher(CorrosiveOozeCombatWatcher.class);
        if (watcher == null) {
            return false;
        }
        MageObjectReference sourceMor = new MageObjectReference(source.getSourceId(), source.getStackMomentSourceZCC(), game);
        List<Permanent> equipments = watcher.getEquipmentsToDestroy(sourceMor)
                .stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        watcher.getRelatedBlockedCreatures(sourceMor)
                .stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .flatMap(p -> p.getAttachments().stream())
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(a -> a.hasSubtype(SubType.EQUIPMENT, game))
                .forEach(equipments::add);
        equipments.forEach(p -> p.destroy(source, game));
        return true;

    }
}

class CorrosiveOozeCombatWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> oozeBlocksOrBlocked = new HashMap<>();
    private final Map<MageObjectReference, Set<MageObjectReference>> oozeEquipmentsToDestroy = new HashMap<>();

    CorrosiveOozeCombatWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.oozeBlocksOrBlocked.clear();
            return;
        }
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent attacker = game.getPermanent(event.getTargetId());
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (attacker == null || blocker == null) {
                return;
            }
            oozeBlocksOrBlocked.computeIfAbsent(new MageObjectReference(attacker, game), k -> new HashSet<>())
                    .add(new MageObjectReference(blocker, game));
            oozeBlocksOrBlocked.computeIfAbsent(new MageObjectReference(blocker, game), k -> new HashSet<>())
                    .add(new MageObjectReference(attacker, game));
            return;
        }

        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                // Check if a previous blocked or blocked by creatures is leaving the battlefield
                for (Map.Entry<MageObjectReference, Set<MageObjectReference>> entry : oozeBlocksOrBlocked.entrySet()) {
                    for (MageObjectReference mor : entry.getValue()) {
                        if (mor.refersTo(zEvent.getTarget(), game)) {
                            // check for equipments and remember
                            zEvent.getTarget().getAttachments().stream()
                                    .map(game::getPermanent)
                                    .filter(Objects::nonNull)
                                    .filter(a -> a.hasSubtype(SubType.EQUIPMENT, game))
                                    .map(a -> new MageObjectReference(a, game))
                                    .forEach(oozeEquipmentsToDestroy.computeIfAbsent(entry.getKey(), k -> new HashSet<>())::add);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        oozeBlocksOrBlocked.clear();
        oozeEquipmentsToDestroy.clear();
    }

    Set<MageObjectReference> getRelatedBlockedCreatures(MageObjectReference ooze) {
        return oozeBlocksOrBlocked.getOrDefault(ooze, Collections.emptySet());
    }

    Set<MageObjectReference> getEquipmentsToDestroy(MageObjectReference ooze) {
        return oozeEquipmentsToDestroy.getOrDefault(ooze, Collections.emptySet());
    }
}
