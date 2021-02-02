
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LoneFox
 */
public final class SpringOfEternalPeace extends CardImpl {

    public SpringOfEternalPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        // You gain 8 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(8));
    }

    private SpringOfEternalPeace(final SpringOfEternalPeace card) {
        super(card);
    }

    @Override
    public SpringOfEternalPeace copy() {
        return new SpringOfEternalPeace(this);
    }
}
