package mage.cards.c;

import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Addendum — If you cast this spell during your main phase, tap that creature and it doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new TapTargetEffect(), AddendumCondition.instance,
                "<br><i>Addendum</i> &mdash; If you cast this spell " +
                        "during your main phase, tap that creature"
        ));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new DontUntapInControllersNextUntapStepSourceEffect(),
                AddendumCondition.instance, "and it doesn't untap " +
                "during its controller's next untap step."
        ));
    }

    private CodeOfConstraint(final CodeOfConstraint card) {
        super(card);
    }

    @Override
    public CodeOfConstraint copy() {
        return new CodeOfConstraint(this);
    }
}
