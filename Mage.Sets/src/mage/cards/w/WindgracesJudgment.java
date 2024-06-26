package mage.cards.w;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WindgracesJudgment extends CardImpl {

    public WindgracesJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{G}");

        // For any number of opponents, destroy target nonland permanent that player controls.
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("For any number of opponents, destroy target nonland permanent that player controls")
        );
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
    }

    private WindgracesJudgment(final WindgracesJudgment card) {
        super(card);
    }

    @Override
    public WindgracesJudgment copy() {
        return new WindgracesJudgment(this);
    }
}
