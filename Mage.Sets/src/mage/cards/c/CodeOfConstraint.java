package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CodeOfConstraint extends CardImpl {

    public CodeOfConstraint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target creature gets -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

        // Addendum — If you cast this spell during your main phase, tap that creature and it doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new CodeOfConstraintEffect());
    }

    private CodeOfConstraint(final CodeOfConstraint card) {
        super(card);
    }

    @Override
    public CodeOfConstraint copy() {
        return new CodeOfConstraint(this);
    }
}

class CodeOfConstraintEffect extends OneShotEffect {

    CodeOfConstraintEffect() {
        super(Outcome.Benefit);
        staticText = "<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, " +
                "tap that creature and it doesn't untap during its controller's next untap step.";
    }

    private CodeOfConstraintEffect(final CodeOfConstraintEffect effect) {
        super(effect);
    }

    @Override
    public CodeOfConstraintEffect copy() {
        return new CodeOfConstraintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AddendumCondition.instance.apply(game, source)) {
            new TapTargetEffect().apply(game, source);
            game.addEffect(new DontUntapInControllersNextUntapStepTargetEffect(), source);
        }
        return true;
    }
}