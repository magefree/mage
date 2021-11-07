package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class WolfStrike extends CardImpl {

    public WolfStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature you control gets +2/+0 until end of turn if it's night.
        // Then it deals damage equal to its power to target creature you don't control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.getSpellAbility().addEffect(new WolfStikeEffect());
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("it").concatBy("Then"));
    }

    private WolfStrike(final WolfStrike card) {
        super(card);
    }

    @Override
    public WolfStrike copy() {
        return new WolfStrike(this);
    }
}

class WolfStikeEffect extends OneShotEffect {

    public WolfStikeEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target creature you control gets +2/+0 until end of turn if it's night";
    }

    private WolfStikeEffect(final WolfStikeEffect effect) {
        super(effect);
    }

    @Override
    public WolfStikeEffect copy() {
        return new WolfStikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.checkDayNight(false)) {
            BoostTargetEffect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
