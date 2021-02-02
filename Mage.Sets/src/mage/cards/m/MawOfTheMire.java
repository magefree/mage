
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class MawOfTheMire extends CardImpl {

    public MawOfTheMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");


        // Destroy target land. You gain 4 life.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private MawOfTheMire(final MawOfTheMire card) {
        super(card);
    }

    @Override
    public MawOfTheMire copy() {
        return new MawOfTheMire(this);
    }
}
