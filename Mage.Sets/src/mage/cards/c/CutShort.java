package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
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
public final class CutShort extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("planeswalker that was activated this turn or tapped creature");

    static {
        filter.add(CutShortPredicate.instance);
    }

    public CutShort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target planeswalker that was activated this turn or tapped creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new CutShortWatcher());
    }

    private CutShort(final CutShort card) {
        super(card);
    }

    @Override
    public CutShort copy() {
        return new CutShort(this);
    }
}

enum CutShortPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.isCreature(game)
                && input.isTapped()
                || input.isPlaneswalker(game)
                && CutShortWatcher.checkPermanent(input, game);
    }
}

class CutShortWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    CutShortWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            set.add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPermanent(Permanent permanent, Game game) {
        return game
                .getState()
                .getWatcher(CutShortWatcher.class)
                .set
                .contains(new MageObjectReference(permanent, game));
    }
}
