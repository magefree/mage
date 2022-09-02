package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class GangOfElk extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(BlockingCreatureCount.SOURCE, 2);

    public GangOfElk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.ELK, SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever Gang of Elk becomes blocked, it gets +2/+2 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true, "it"), false));
    }

    private GangOfElk(final GangOfElk card) {
        super(card);
    }

    @Override
    public GangOfElk copy() {
        return new GangOfElk(this);
    }
}
