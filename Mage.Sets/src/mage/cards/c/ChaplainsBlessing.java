
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ChaplainsBlessing extends CardImpl {

    public ChaplainsBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // You gain 5 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(5));        
    }

    private ChaplainsBlessing(final ChaplainsBlessing card) {
        super(card);
    }

    @Override
    public ChaplainsBlessing copy() {
        return new ChaplainsBlessing(this);
    }
}
