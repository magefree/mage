
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("1st"));

        // Target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1).setTargetPointer(new SecondTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("2nd"));

        // Target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1).setTargetPointer(new ThirdTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("3rd"));

    }

    private SeedsOfStrength(final SeedsOfStrength card) {
        super(card);
    }

    @Override
    public SeedsOfStrength copy() {
        return new SeedsOfStrength(this);
    }
}
