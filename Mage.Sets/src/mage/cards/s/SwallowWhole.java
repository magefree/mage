package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwallowWhole extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("an untapped creature you control");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter2.add(TappedPredicate.TAPPED);
    }

    public SwallowWhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // As an additional cost to cast this spell, tap an untapped creature you control.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledPermanent(filter)));

        // Exile target tapped creature. Put a +1/+1 counter on the creature tapped to cast this spell.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addEffect(new SwallowWholeEffect());
    }

    private SwallowWhole(final SwallowWhole card) {
        super(card);
    }

    @Override
    public SwallowWhole copy() {
        return new SwallowWhole(this);
    }
}

class SwallowWholeEffect extends OneShotEffect {

    SwallowWholeEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on the creature tapped to pay this spell's additional cost.";
    }

    private SwallowWholeEffect(final SwallowWholeEffect effect) {
        super(effect);
    }

    @Override
    public SwallowWholeEffect copy() {
        return new SwallowWholeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getCosts()
                .stream()
                .filter(TapTargetCost.class::isInstance)
                .map(TapTargetCost.class::cast)
                .map(TapTargetCost::getTarget)
                .map(Target::getFirstTarget)
                .map(game::getPermanent)
                .findFirst()
                .orElse(null);
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
    }
}
