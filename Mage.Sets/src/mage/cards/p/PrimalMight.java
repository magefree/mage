package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class PrimalMight extends CardImpl {

    public PrimalMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Target creature you control gets +X/+X until end of turn. Then it fights up to one target creature you don’t control.
        this.getSpellAbility().addEffect(new PrimalMightEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    public PrimalMight(final PrimalMight card) {
        super(card);
    }

    @Override
    public PrimalMight copy() {
        return new PrimalMight(this);
    }
}

class PrimalMightEffect extends OneShotEffect {

    PrimalMightEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control gets +X/+X until end of turn. " +
                "Then it fights up to one target creature you don’t control.";
    }

    private PrimalMightEffect(final PrimalMightEffect effect) {
        super(effect);
    }

    @Override
    public PrimalMightEffect copy() {
        return new PrimalMightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        ContinuousEffect effect = new BoostTargetEffect(ManacostVariableValue.instance, ManacostVariableValue.instance, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        game.applyEffects();
        return new FightTargetsEffect().apply(game, source);
    }
}
