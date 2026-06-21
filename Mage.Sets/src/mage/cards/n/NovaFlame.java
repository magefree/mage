package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class NovaFlame extends CardImpl {

    public NovaFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{R}{R}");

        // Put X +1/+1 counters on target creature you control. It deals damage equal to its power to each other creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
            CounterType.P1P1.createInstance(), GetXValue.instance
        ));
        this.getSpellAbility().addEffect(new NovaFlameEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private NovaFlame(final NovaFlame card) {
        super(card);
    }

    @Override
    public NovaFlame copy() {
        return new NovaFlame(this);
    }
}

class NovaFlameEffect extends OneShotEffect {

    NovaFlameEffect() {
        super(Outcome.Damage);
        staticText = "It deals damage equal to its power to each other creature";
    }

    private NovaFlameEffect(final NovaFlameEffect effect) {
        super(effect);
    }

    @Override
    public NovaFlameEffect copy() {
        return new NovaFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (!creature.getId().equals(permanent.getId())) {
                creature.damage(power, permanent.getId(), source, game);
            }
        }
        return true;
    }
}
