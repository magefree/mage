
package mage.cards.f;

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
 * @author Loki
 */
public final class FlowstoneCharger extends CardImpl {

    public FlowstoneCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        this.subtype.add(SubType.BEAST);


        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(3, -3, Duration.EndOfTurn), false));
    }

    private FlowstoneCharger(final FlowstoneCharger card) {
        super(card);
    }

    @Override
    public FlowstoneCharger copy() {
        return new FlowstoneCharger(this);
    }
}
