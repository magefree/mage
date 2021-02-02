
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.target.targetpointer.ThirdTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class SeedsOfStrength extends CardImpl {

    public SeedsOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{W}");

        // Target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (1st)")));
        // Target creature gets +1/+1 until end of turn.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn).setText("<br>Target creature gets +1/+1 until end of turn.");
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (2nd)")));
        // Target creature gets +1/+1 until end of turn.
        effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn).setText("<br>Target creature gets +1/+1 until end of turn.");
        effect.setTargetPointer(new ThirdTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (3rd)")));
    }

    private SeedsOfStrength(final SeedsOfStrength card) {
        super(card);
    }

    @Override
    public SeedsOfStrength copy() {
        return new SeedsOfStrength(this);
    }
}
