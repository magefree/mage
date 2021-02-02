
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class Tarpan extends CardImpl {

    public Tarpan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Tarpan dies, you gain 1 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(1), false));
    }

    private Tarpan(final Tarpan card) {
        super(card);
    }

    @Override
    public Tarpan copy() {
        return new Tarpan(this);
    }
}
