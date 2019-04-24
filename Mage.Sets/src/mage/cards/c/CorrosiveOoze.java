
package mage.cards.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author rscoates
 */
public final class CorrosiveOoze extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("equipped creature");

    static {
        filter.add(new EquippedPredicate());
    }

    public CorrosiveOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.OOZE);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Corrosive Ooze blocks or becomes blocked by an equipped creature, destroy all Equipment attached to that creature at end of combat.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new CorrosiveOozeEffect()), true);
        this.addAbility(new BlocksOrBecomesBlockedTriggeredAbility(effect, filter, false), new CorrosiveOozeCombatWatcher());
    }

    public CorrosiveOoze(final CorrosiveOoze card) {
        super(card);
    }

    @Override
    public CorrosiveOoze copy() {
        return new CorrosiveOoze(this);
    }
}

class CorrosiveOozeEffect extends OneShotEffect {

    public CorrosiveOozeEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all Equipment attached to that creature at end of combat";
    }

    public CorrosiveOozeEffect(final CorrosiveOozeEffect effect) {
        super(effect);
    }

    @Override
    public CorrosiveOozeEffect copy() {
        return new CorrosiveOozeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CorrosiveOozeCombatWatcher watcher = (CorrosiveOozeCombatWatcher) game.getState().getWatchers().get(CorrosiveOozeCombatWatcher.class.getSimpleName());
        if (controller != null && watcher != null) {
            MageObjectReference sourceMor = new MageObjectReference(source.getSourceObject(game), game);
            // get equipmentsToDestroy of creatres already left the battlefield
            List<Permanent> toDestroy = new ArrayList<>();
            Set<MageObjectReference> toDestroyMor = watcher.getEquipmentsToDestroy(sourceMor);
            if (toDestroyMor != null) {
                for (MageObjectReference mor : toDestroyMor) {
                    Permanent attachment = mor.getPermanent(game);
                    if (attachment != null) {
                        toDestroy.add(attachment);
                    }
                }
            }
            // get the related creatures
            Set<MageObjectReference> relatedCreatures = watcher.getRelatedBlockedCreatures(sourceMor);
            if (relatedCreatures != null) {
                for (MageObjectReference relatedCreature : relatedCreatures) {
                    Permanent permanent = relatedCreature.getPermanent(game);
                    if (permanent != null) {
                        for (UUID attachmentId : permanent.getAttachments()) {
                            Permanent attachment = game.getPermanent(attachmentId);
                            if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                                toDestroy.add(attachment);
                            }
                        }
                    }
                }
            }
            for (Permanent permanent : toDestroy) {
                permanent.destroy(source.getSourceId(), game, false);
            }

            return true;
        }
        return false;

    }
}

class CorrosiveOozeCombatWatcher extends Watcher {

    public final HashMap<MageObjectReference, HashSet<MageObjectReference>> oozeBlocksOrBlocked = new HashMap<>();
    public final HashMap<MageObjectReference, HashSet<MageObjectReference>> oozeEquipmentsToDestroy = new HashMap<>();

    public CorrosiveOozeCombatWatcher() {
        super(CorrosiveOozeCombatWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CorrosiveOozeCombatWatcher(final CorrosiveOozeCombatWatcher watcher) {
        super(watcher);
        for (Map.Entry<MageObjectReference, HashSet<MageObjectReference>> entry : watcher.oozeBlocksOrBlocked.entrySet()) {
            HashSet<MageObjectReference> newSet = new HashSet<>(entry.getValue());
            oozeBlocksOrBlocked.put(entry.getKey(), newSet);
        }
        for (Map.Entry<MageObjectReference, HashSet<MageObjectReference>> entry : watcher.oozeEquipmentsToDestroy.entrySet()) {
            HashSet<MageObjectReference> newSet = new HashSet<>(entry.getValue());
            oozeEquipmentsToDestroy.put(entry.getKey(), newSet);
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.oozeBlocksOrBlocked.clear();
        }
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Permanent attacker = game.getPermanent(event.getTargetId());
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (attacker != null && attacker.getName().equals("Corrosive Ooze")) { // To check for name is not working if Ooze is copied but name changed
                if (blocker != null && hasAttachedEquipment(game, blocker)) {
                    MageObjectReference oozeMor = new MageObjectReference(attacker, game);
                    HashSet<MageObjectReference> relatedCreatures = oozeBlocksOrBlocked.getOrDefault(oozeMor, new HashSet<>());
                    relatedCreatures.add(new MageObjectReference(event.getSourceId(), game));
                    oozeBlocksOrBlocked.put(oozeMor, relatedCreatures);
                }
            }
            if (blocker != null && blocker.getName().equals("Corrosive Ooze")) {
                if (attacker != null && hasAttachedEquipment(game, attacker)) {
                    MageObjectReference oozeMor = new MageObjectReference(blocker, game);
                    HashSet<MageObjectReference> relatedCreatures = oozeBlocksOrBlocked.getOrDefault(oozeMor, new HashSet<>());
                    relatedCreatures.add(new MageObjectReference(event.getTargetId(), game));
                    oozeBlocksOrBlocked.put(oozeMor, relatedCreatures);
                }
            }
        }

        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
                if (game.getTurn() != null && TurnPhase.COMBAT == game.getTurn().getPhaseType()) {
                    // Check if a previous blocked or blocked by creatures is leaving the battlefield
                    for (Map.Entry<MageObjectReference, HashSet<MageObjectReference>> entry : oozeBlocksOrBlocked.entrySet()) {
                        for (MageObjectReference mor : entry.getValue()) {
                            if (mor.refersTo(((ZoneChangeEvent) event).getTarget(), game)) {
                                // check for equipments and remember
                                for (UUID attachmentId : ((ZoneChangeEvent) event).getTarget().getAttachments()) {
                                    Permanent attachment = game.getPermanent(attachmentId);
                                    if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                                        HashSet<MageObjectReference> toDestroy = oozeEquipmentsToDestroy.getOrDefault(entry.getKey(), new HashSet<>());
                                        toDestroy.add(new MageObjectReference(attachment, game));
                                        oozeEquipmentsToDestroy.put(entry.getKey(), toDestroy);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasAttachedEquipment(Game game, Permanent permanent) {
        for (UUID attachmentId : permanent.getAttachments()) {
            Permanent attachment = game.getPermanent(attachmentId);
            if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                return true;
            }
        }
        return false;
    }

    public Set<MageObjectReference> getRelatedBlockedCreatures(MageObjectReference ooze) {
        Set<MageObjectReference> relatedCreatures = this.oozeBlocksOrBlocked.get(ooze);
        oozeBlocksOrBlocked.remove(ooze); // remove here to get no overlap with creatures leaving meanwhile
        return relatedCreatures;
    }

    public Set<MageObjectReference> getEquipmentsToDestroy(MageObjectReference ooze) {
        Set<MageObjectReference> equipmentsToDestroy = this.oozeEquipmentsToDestroy.get(ooze);
        oozeEquipmentsToDestroy.remove(ooze); // remove here to get no overlap with creatures leaving meanwhile
        return equipmentsToDestroy;
    }

    @Override
    public CorrosiveOozeCombatWatcher copy() {
        return new CorrosiveOozeCombatWatcher(this);
    }

}
