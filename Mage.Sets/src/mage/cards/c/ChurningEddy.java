package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author LoneFox
 */
public final class ChurningEddy extends CardImpl {

    public ChurningEddy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Return target creature and target land to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private ChurningEddy(final ChurningEddy card) {
        super(card);
    }

    @Override
    public ChurningEddy copy() {
        return new ChurningEddy(this);
    }
}
