package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExponentialGrowth extends CardImpl {

    public ExponentialGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}{G}");

        // Until end of turn, double target creature's power X times.
        this.getSpellAbility().addEffect(new ExponentialGrowthEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ExponentialGrowth(final ExponentialGrowth card) {
        super(card);
    }

    @Override
    public ExponentialGrowth copy() {
        return new ExponentialGrowth(this);
    }
}

class ExponentialGrowthEffect extends OneShotEffect {

    ExponentialGrowthEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, double target creature's power X times";
    }

    private ExponentialGrowthEffect(final ExponentialGrowthEffect effect) {
        super(effect);
    }

    @Override
    public ExponentialGrowthEffect copy() {
        return new ExponentialGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        int multiplier = 1;
        for (int i = 0; i < xValue; i++) {
            multiplier = CardUtil.overflowMultiply(multiplier, 2);
        }
        multiplier = CardUtil.overflowDec(multiplier, 1);
        game.addEffect(new BoostTargetEffect(CardUtil.overflowMultiply(
                multiplier, permanent.getPower().getValue()
        ), 0, Duration.EndOfTurn), source);
        return true;
    }
}
