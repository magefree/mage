package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachOpponentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElminstersSimulacrum extends CardImpl {

    public ElminstersSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // For each opponent, you create a token that's a copy of up to one target creature that player controls.
        this.getSpellAbility().addEffect(new ElminstersSimulacrumAdjusterEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,1));
        this.getSpellAbility().setTargetAdjuster(new ForEachOpponentTargetsAdjuster());
    }

    private ElminstersSimulacrum(final ElminstersSimulacrum card) {
        super(card);
    }

    @Override
    public ElminstersSimulacrum copy() {
        return new ElminstersSimulacrum(this);
    }
}

class ElminstersSimulacrumAdjusterEffect extends OneShotEffect {

    ElminstersSimulacrumAdjusterEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new EachTargetPointer());
        staticText = "for each opponent, you create a token that's a copy of up to one target creature that player controls";
    }

    private ElminstersSimulacrumAdjusterEffect(final ElminstersSimulacrumAdjusterEffect effect) {
        super(effect);
    }

    @Override
    public ElminstersSimulacrumAdjusterEffect copy() {
        return new ElminstersSimulacrumAdjusterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setSavedPermanent(permanent);
            effect.apply(game, source);
        }
        return true;
    }
}
