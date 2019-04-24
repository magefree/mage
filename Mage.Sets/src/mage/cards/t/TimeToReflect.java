
package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author jeffwadsworth
 */
public final class TimeToReflect extends CardImpl {

    public TimeToReflect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature that blocked or was blocked by a Zombie this turn.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterCreaturePermanent("creature that blocked or was blocked by a Zombie this turn.")));
        this.getSpellAbility().addWatcher(new BlockedOrWasBlockedByAZombieWatcher());

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            List<PermanentIdPredicate> creaturesThatBlockedOrWereBlockedByAZombie = new ArrayList<>();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that blocked or was blocked by a Zombie this turn.").copy();
            BlockedOrWasBlockedByAZombieWatcher watcher = (BlockedOrWasBlockedByAZombieWatcher) game.getState().getWatchers().get(BlockedOrWasBlockedByAZombieWatcher.class.getSimpleName());
            if (watcher != null) {
                for (MageObjectReference mor : watcher.getBlockedThisTurnCreatures()) {
                    Permanent permanent = mor.getPermanent(game);
                    if (permanent != null) {
                        creaturesThatBlockedOrWereBlockedByAZombie.add(new PermanentIdPredicate(permanent.getId()));
                    }
                }
            }
            filter.add(Predicates.or(creaturesThatBlockedOrWereBlockedByAZombie));
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }

    public TimeToReflect(final TimeToReflect card) {
        super(card);
    }

    @Override
    public TimeToReflect copy() {
        return new TimeToReflect(this);
    }
}

class BlockedOrWasBlockedByAZombieWatcher extends Watcher {

    private final Set<MageObjectReference> blockedOrWasBlockedByAZombieWatcher;

    public BlockedOrWasBlockedByAZombieWatcher() {
        super(BlockedOrWasBlockedByAZombieWatcher.class.getSimpleName(), WatcherScope.GAME);
        blockedOrWasBlockedByAZombieWatcher = new HashSet<>();
    }

    public BlockedOrWasBlockedByAZombieWatcher(final BlockedOrWasBlockedByAZombieWatcher watcher) {
        super(watcher);
        blockedOrWasBlockedByAZombieWatcher = new HashSet<>(watcher.blockedOrWasBlockedByAZombieWatcher);
    }

    @Override
    public BlockedOrWasBlockedByAZombieWatcher copy() {
        return new BlockedOrWasBlockedByAZombieWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            if (game.getPermanent(event.getTargetId()).hasSubtype(SubType.ZOMBIE, game)) {
                this.blockedOrWasBlockedByAZombieWatcher.add(new MageObjectReference(event.getSourceId(), game));
            }
            if (game.getPermanent(event.getSourceId()).hasSubtype(SubType.ZOMBIE, game)) {
                this.blockedOrWasBlockedByAZombieWatcher.add(new MageObjectReference(event.getTargetId(), game));
            }
        }
    }

    public Set<MageObjectReference> getBlockedThisTurnCreatures() {
        return this.blockedOrWasBlockedByAZombieWatcher;
    }

    @Override
    public void reset() {
        super.reset();
        blockedOrWasBlockedByAZombieWatcher.clear();
    }

}
