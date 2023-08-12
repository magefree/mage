package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrayingLine extends CardImpl {

    public FrayingLine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Fraying Line enters the battlefield, put a rope counter on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.ROPE.createInstance())
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of each player's upkeep, that player may pay {2}. If they do, they put a rope counter on a creature they control. Otherwise, exile Fraying Line and each creature without a rope counter on it, then remove all rope counters from all creatures.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new FrayingLineEffect(), TargetController.ACTIVE, false
        ));
    }

    private FrayingLine(final FrayingLine card) {
        super(card);
    }

    @Override
    public FrayingLine copy() {
        return new FrayingLine(this);
    }
}

class FrayingLineEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(CounterType.ROPE.getPredicate()));
    }

    FrayingLineEffect() {
        super(Outcome.Benefit);
        staticText = "that player may pay {2}. If they do, they put a rope counter on a creature they control. " +
                "Otherwise, exile {this} and each creature without a rope counter on it, " +
                "then remove all rope counters from all creatures";
    }

    private FrayingLineEffect(final FrayingLineEffect effect) {
        super(effect);
    }

    @Override
    public FrayingLineEffect copy() {
        return new FrayingLineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        Cost cost = new GenericManaCost(2);
        if (cost.canPay(source, source, player.getId(), game)
                && player.chooseUse(outcome, "Pay {2}?", source, game)
                && cost.pay(source, game, source, player.getId(), false)) {
            if (!game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1)) {
                return true;
            }
            TargetPermanent target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.ROPE.createInstance(), player.getId(), source, game);
            }
            return true;
        }
        Cards cards = new CardsImpl(source.getSourcePermanentIfItStillExists(game));
        cards.addAllCards(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            int counters = permanent.getCounters(game).getCount(CounterType.ROPE);
            if (counters > 0) {
                permanent.removeCounters(CounterType.ROPE.createInstance(counters), source, game);
            }
        }
        return true;
    }
}
