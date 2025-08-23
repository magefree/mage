package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerParityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author werhsdnas
 */
public final class ZimonesHypothesis extends CardImpl {

    public ZimonesHypothesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // You may put a +1/+1 counter on a creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, true));

        //Then choose odd or even. Return each creature with power of the chosen quality to its owner’s hand.
        this.getSpellAbility().addEffect(new ZimonesHypothesisBounceEffect());
    }

    private ZimonesHypothesis(final ZimonesHypothesis card) {
        super(card);
    }

    @Override
    public ZimonesHypothesis copy() {
        return new ZimonesHypothesis(this);
    }
}

class ZimonesHypothesisBounceEffect extends OneShotEffect {

    private static final FilterPermanent evenFilter = new FilterCreaturePermanent();
    private static final FilterPermanent oddFilter = new FilterCreaturePermanent();

    static {
        evenFilter.add(PowerParityPredicate.EVEN);
        oddFilter.add(PowerParityPredicate.ODD);
    }

    ZimonesHypothesisBounceEffect() {
        super(Outcome.Benefit);
        staticText = "Choose odd or even. Return each creature with power of the chosen quality to its owner's hand. <i>(Zero is even.)</i>";
    }

    private ZimonesHypothesisBounceEffect(final ZimonesHypothesisBounceEffect effect) {
        super(effect);
    }

    @Override
    public ZimonesHypothesisBounceEffect copy() {
        return new ZimonesHypothesisBounceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterPermanent filter = player.chooseUse(
                outcome, "Odd or even?", null,
                "Odd", "Even", source, game
        ) ? oddFilter : evenFilter;

        return player.moveCards(
                game.getBattlefield().getActivePermanents(
                        filter, source.getControllerId(), source, game
                ).stream().collect(Collectors.toSet()), Zone.HAND, source, game
        );
    }
}