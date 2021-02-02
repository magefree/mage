
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author daagar
 */
public final class ChargingPaladin extends CardImpl {

    public ChargingPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Charging Paladin attacks, it gets +0/+3 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(0, 3, Duration.EndOfTurn), false));
    }

    private ChargingPaladin(final ChargingPaladin card) {
        super(card);
    }

    @Override
    public ChargingPaladin copy() {
        return new ChargingPaladin(this);
    }
}
