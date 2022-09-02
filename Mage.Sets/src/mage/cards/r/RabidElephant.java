package mage.cards.r;

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
 * @author LevelX2
 */
public final class RabidElephant extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(BlockingCreatureCount.SOURCE, 2);

    public RabidElephant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Rabid Elephant becomes blocked, it gets +2/+2 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true, "it"), false));
    }

    private RabidElephant(final RabidElephant card) {
        super(card);
    }

    @Override
    public RabidElephant copy() {
        return new RabidElephant(this);
    }
}
