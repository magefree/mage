
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class ShieldmatesBlessing extends CardImpl {

    public ShieldmatesBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ShieldmatesBlessing(final ShieldmatesBlessing card) {
        super(card);
    }

    @Override
    public ShieldmatesBlessing copy() {
        return new ShieldmatesBlessing(this);
    }
}
