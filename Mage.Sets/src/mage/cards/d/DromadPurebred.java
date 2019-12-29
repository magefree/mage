
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Derpthemeus
 */
public final class DromadPurebred extends CardImpl {

    public DromadPurebred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.CAMEL);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Whenever Dromad Purebred is dealt damage, you gain 1 life.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new GainLifeEffect(1), false));
    }

    public DromadPurebred(final DromadPurebred card) {
        super(card);
    }

    @Override
    public DromadPurebred copy() {
        return new DromadPurebred(this);
    }
}
