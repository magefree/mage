package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThrowFromTheSaddle extends CardImpl {

    public ThrowFromTheSaddle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target creature you control gets +1/+1 until end of turn. Put a +1/+1 counter on it instead if it's a Mount. Then it deals damage equal to its power to target creature you don't control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new ThrowFromTheSaddleEffect());
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect().concatBy("Then"));
    }

    private ThrowFromTheSaddle(final ThrowFromTheSaddle card) {
        super(card);
    }

    @Override
    public ThrowFromTheSaddle copy() {
        return new ThrowFromTheSaddle(this);
    }
}

class ThrowFromTheSaddleEffect extends OneShotEffect {

    ThrowFromTheSaddleEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target creature you control gets +1/+1 until end of turn. "
                + "Put a +1/+1 counter on it instead if it's a Mount.";
    }

    private ThrowFromTheSaddleEffect(final ThrowFromTheSaddleEffect effect) {
        super(effect);
    }

    @Override
    public ThrowFromTheSaddleEffect copy() {
        return new ThrowFromTheSaddleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (SubType.MOUNT.getPredicate().apply(permanent, game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        } else {
            game.addEffect(
                    new BoostTargetEffect(1, 1)
                            .setTargetPointer(new FixedTarget(permanent.getId())),
                    source
            );
        }
        return true;
    }

}