package mage.cards.b;

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
 * @author Ryan-Saklad
 */
public final class BountyOfMight extends CardImpl {

    public BountyOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}{G}");

        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("1st"));

        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3).setTargetPointer(new SecondTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("2nd"));

        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3).setTargetPointer(new ThirdTargetPointer()).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("3rd"));

    }

    private BountyOfMight(final BountyOfMight card) {
        super(card);
    }

    @Override
    public BountyOfMight copy() {
        return new BountyOfMight(this);
    }
}
