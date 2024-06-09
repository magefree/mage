package mage.cards.y;

import mage.MageObjectReference;
import mage.abilities.effects.common.DestroyTargetEffect;
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
public final class YouCannotPass extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature that blocked or was blocked by a legendary creature this turn");

    static {
        filter.add(YouCannotPassPredicate.instance);
    }

    public YouCannotPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Destroy target creature that blocked or was blocked by a legendary creature this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new YouCannotPassWatcher());
    }

    private YouCannotPass(final YouCannotPass card) {
        super(card);
    }

    @Override
    public YouCannotPass copy() {
        return new YouCannotPass(this);
    }
}

enum YouCannotPassPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        YouCannotPassWatcher watcher = game.getState().getWatcher(YouCannotPassWatcher.class);
        return watcher != null && watcher.checkCreature(input, game);
    }
}

class YouCannotPassWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    YouCannotPassWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.BLOCKER_DECLARED) {
            return;
        }
        Permanent attacker = game.getPermanent(event.getTargetId());
        Permanent blocker = game.getPermanent(event.getSourceId());
        if (attacker == null || blocker == null) {
            return;
        }
        if (attacker.isLegendary(game)) {
            set.add(new MageObjectReference(blocker, game));
        }
        if (blocker.isLegendary(game)) {
            set.add(new MageObjectReference(attacker, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    boolean checkCreature(Permanent permanent, Game game) {
        return set.contains(new MageObjectReference(permanent, game));
    }
}
