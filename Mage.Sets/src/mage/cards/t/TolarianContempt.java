package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TolarianContempt extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature your opponents control");

    public TolarianContempt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // When Tolarian Contempt enters the battlefield, put a rejection counter on each creature your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.REJECTION.createInstance(), filter)
        ));

        // At the beginning of your end step, for each opponent, choose up to one target creature they control with a rejection counter on it. That creature's owner puts it on the top or bottom of their library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new TolarianContemptEffect(), TargetController.YOU, false
        ).setTargetAdjuster(TolarianContemptAdjuster.instance));
    }

    private TolarianContempt(final TolarianContempt card) {
        super(card);
    }

    @Override
    public TolarianContempt copy() {
        return new TolarianContempt(this);
    }
}

enum TolarianContemptAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent(
                    "creature controlled by " + opponent.getName() + " with a rejection counter on it"
            );
            filter.add(CounterType.REJECTION.getPredicate());
            filter.add(new ControllerIdPredicate(opponentId));
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}

class TolarianContemptEffect extends OneShotEffect {

    TolarianContemptEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, choose up to one target creature they control with a rejection " +
                "counter on it. That creature's owner puts it on the top or bottom of their library";
        this.setTargetPointer(new EachTargetPointer());
    }

    private TolarianContemptEffect(final TolarianContemptEffect effect) {
        super(effect);
    }

    @Override
    public TolarianContemptEffect copy() {
        return new TolarianContemptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID creatureId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(creatureId);
            Player owner = game.getPlayer(game.getOwnerId(creatureId));
            if (permanent == null || owner == null) {
                continue;
            }
            if (owner.chooseUse(
                    Outcome.Neutral, "Put " + permanent.getIdName() + " on the top or bottom of your library?",
                    null, "Top", "Bottom", source, game
            )) {
                owner.putCardsOnTopOfLibrary(permanent, game, source, false);
            } else {
                owner.putCardsOnBottomOfLibrary(permanent, game, source, false);
            }
        }
        return true;
    }
}
