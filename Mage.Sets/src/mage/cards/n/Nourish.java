

package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class Nourish extends CardImpl {

    public Nourish (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}{G}");

        this.getSpellAbility().addEffect(new GainLifeEffect(6));
    }

    private Nourish(final Nourish card) {
        super(card);
    }

    @Override
    public Nourish copy() {
        return new Nourish(this);
    }

}
