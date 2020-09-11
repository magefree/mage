package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SizzlingBarrage extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that blocked this turn");

    static {
        filter.add(SizzlingBarragePredicate.instance);
    }

    public SizzlingBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Sizzling Barrage deals 4 damage to target creature that blocked this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new SizzlingBarrageWatcher());
    }

    private SizzlingBarrage(final SizzlingBarrage card) {
        super(card);
    }

    @Override
    public SizzlingBarrage copy() {
        return new SizzlingBarrage(this);
    }
}

enum SizzlingBarragePredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        SizzlingBarrageWatcher watcher = game.getState().getWatcher(SizzlingBarrageWatcher.class);
        return watcher != null && watcher.checkCreature(input, game);
    }
}

class SizzlingBarrageWatcher extends Watcher {

    private final Set<MageObjectReference> blockers = new HashSet<>();

    SizzlingBarrageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.BLOCKER_DECLARED) {
            return;
        }
        blockers.add(new MageObjectReference(event.getSourceId(), game));
    }

    @Override
    public void reset() {
        super.reset();
        this.blockers.clear();
    }

    boolean checkCreature(Permanent permanent, Game game) {
        return blockers.stream().anyMatch(mor -> mor.refersTo(permanent, game));
    }
}
