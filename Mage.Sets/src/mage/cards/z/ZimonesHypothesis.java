package mage.cards.z;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerParityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author matoro
 * Based on initial version by werhsdnas
 */
public final class ZimonesHypothesis extends CardImpl {

    public ZimonesHypothesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // You may put a +1/+1 counter on a creature.
        this.getSpellAbility().addEffect(new ZimonesHypothesisCounterEffect());

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

class ZimonesHypothesisCounterEffect extends OneShotEffect {

    ZimonesHypothesisCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "You may put a +1/+1 counter on a creature.";
    }

    private ZimonesHypothesisCounterEffect(final ZimonesHypothesisCounterEffect effect) {
        super(effect);
    }

    @Override
    public ZimonesHypothesisCounterEffect copy() {
        return new ZimonesHypothesisCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        final Target target = new TargetCreaturePermanent(0, 1).withNotTarget(true);
        player.choose(this.outcome, target, source, game);
        final Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
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
        super(Outcome.ReturnToHand);
        this.staticText = "<br>Then choose odd or even. Return each creature with power of the chosen quality to its owner's hand. <i>(Zero is even.)</i>";
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
        final Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        final FilterPermanent filter = player.chooseUse(this.outcome, "Odd or even?", null, "Odd", "Even", source, game) ? oddFilter : evenFilter;
        return new ReturnToHandFromBattlefieldAllEffect(filter).apply(game, source);
    }
}
