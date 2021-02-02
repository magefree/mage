
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class Omniscience extends CardImpl {

    public Omniscience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{7}{U}{U}{U}");

        // You may cast nonland cards from your hand without paying their mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastFromHandWithoutPayingManaCostEffect()));
    }

    private Omniscience(final Omniscience card) {
        super(card);
    }

    @Override
    public Omniscience copy() {
        return new Omniscience(this);
    }
}

