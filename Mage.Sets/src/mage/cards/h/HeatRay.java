
package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class HeatRay extends CardImpl {

    public HeatRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{R}");


        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HeatRay(final HeatRay card) {
        super(card);
    }

    @Override
    public HeatRay copy() {
        return new HeatRay(this);
    }
}
