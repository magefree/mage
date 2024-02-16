
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class ChargingBandits extends CardImpl {

    public ChargingBandits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Charging Bandits attacks, it gets +2/+0 until end of turn.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    private ChargingBandits(final ChargingBandits card) {
        super(card);
    }

    @Override
    public ChargingBandits copy() {
        return new ChargingBandits(this);
    }
}
