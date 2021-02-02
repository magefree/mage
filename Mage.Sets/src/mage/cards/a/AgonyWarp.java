
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author North
 */
public final class AgonyWarp extends CardImpl {

    public AgonyWarp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{B}");


        // Target creature gets -3/-0 until end of turn.
        Effect effect = new BoostTargetEffect(-3,0, Duration.EndOfTurn);
        effect.setText("Target creature gets -3/-0 until end of turn");
        this.getSpellAbility().addEffect(effect);
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent("first creature"));
        this.getSpellAbility().addTarget(target);

        // Target creature gets -0/-3 until end of turn.
        Effect effect2 = new BoostTargetEffect(-0,-3, Duration.EndOfTurn);
        effect2.setText("<br><br>Target creature gets -0/-3 until end of turn");
        effect2.setTargetPointer(SecondTargetPointer.getInstance());
        this.getSpellAbility().addEffect(effect2);
        target = new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)"));
        this.getSpellAbility().addTarget(target);
    }

    private AgonyWarp(final AgonyWarp card) {
        super(card);
    }

    @Override
    public AgonyWarp copy() {
        return new AgonyWarp(this);
    }
}
