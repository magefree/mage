
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class VoltCharge extends CardImpl {

    public VoltCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    public VoltCharge(final VoltCharge card) {
        super(card);
    }

    @Override
    public VoltCharge copy() {
        return new VoltCharge(this);
    }
}
