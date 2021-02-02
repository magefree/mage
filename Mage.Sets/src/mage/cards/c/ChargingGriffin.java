
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author jeffwadsworth
 */
public final class ChargingGriffin extends CardImpl {

    public ChargingGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Charging Griffin attacks, it gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
        
    }

    private ChargingGriffin(final ChargingGriffin card) {
        super(card);
    }

    @Override
    public ChargingGriffin copy() {
        return new ChargingGriffin(this);
    }
}
