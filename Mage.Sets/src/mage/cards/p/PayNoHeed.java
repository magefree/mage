
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageBySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeffwadsworth
 */
public final class PayNoHeed extends CardImpl {

    public PayNoHeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Prevent all damage a source of your choice would deal this turn.
        this.getSpellAbility().addEffect(new PreventDamageBySourceEffect());
        
    }

    private PayNoHeed(final PayNoHeed card) {
        super(card);
    }

    @Override
    public PayNoHeed copy() {
        return new PayNoHeed(this);
    }
}
