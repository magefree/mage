package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreacherousGreed extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature that dealt damage this turn");

    static {
        filter.add(TreacherousGreedPredicate.instance);
    }

    public TreacherousGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{B}");

        // As an additional cost to cast this spell, sacrifice a creature that dealt damage this turn.
        this.getSpellAbility().addCost(new SacrificeTargetCost(filter));
        this.getSpellAbility().addWatcher(new TreacherousGreedWatcher());

        // Draw three cards. Each opponent loses 3 life and you gain 3 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("and"));
    }

    private TreacherousGreed(final TreacherousGreed card) {
        super(card);
    }

    @Override
    public TreacherousGreed copy() {
        return new TreacherousGreed(this);
    }
}

enum TreacherousGreedPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return TreacherousGreedWatcher.checkCreature(input, game);
    }
}

class TreacherousGreedWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    TreacherousGreedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
            case DAMAGED_PERMANENT:
                break;
            default:
                return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            set.add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        set.clear();
        super.reset();
    }

    static boolean checkCreature(Permanent permanent, Game game) {
        return game
                .getState()
                .getWatcher(TreacherousGreedWatcher.class)
                .set
                .contains(new MageObjectReference(permanent, game));
    }
}
