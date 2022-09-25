package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ConsumingVortex extends CardImpl {

    public ConsumingVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.ARCANE);


        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("return to hand"));
        // Splice onto Arcane {3}{U}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{3}{U}"));
    }

    private ConsumingVortex(final ConsumingVortex card) {
        super(card);
    }

    @Override
    public ConsumingVortex copy() {
        return new ConsumingVortex(this);
    }
}
