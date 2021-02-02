
package mage.cards.m;

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
 * @author LevelX2
 */
public final class MartialGlory extends CardImpl {

    public MartialGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{W}");


        // Target creature gets +3/+0 until end of turn.
        Effect effect = new BoostTargetEffect(3,0, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+0 until end of turn");
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent("first creature"));
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(target);
        
        // Target creature gets +0/+3 until end of turn.
        Effect effect2 = new BoostTargetEffect(0,3, Duration.EndOfTurn);
        effect2.setText("<br><br>Target creature gets +0/+3 until end of turn");
        effect2.setTargetPointer(SecondTargetPointer.getInstance());
        target = new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)"));
        this.getSpellAbility().addEffect(effect2);
        this.getSpellAbility().addTarget(target);

        
    }

    private MartialGlory(final MartialGlory card) {
        super(card);
    }

    @Override
    public MartialGlory copy() {
        return new MartialGlory(this);
    }
}
