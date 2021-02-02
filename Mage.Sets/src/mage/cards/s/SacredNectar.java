
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class SacredNectar extends CardImpl {

    public SacredNectar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");


        // You gain 4 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private SacredNectar(final SacredNectar card) {
        super(card);
    }

    @Override
    public SacredNectar copy() {
        return new SacredNectar(this);
    }
}
