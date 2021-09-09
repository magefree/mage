package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillowGeist extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public WillowGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on Willow Geist.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // When Willow Geist dies, you gain life equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(
                new GainLifeEffect(xValue).setText("you gain life equal to its power")
        ));
    }

    private WillowGeist(final WillowGeist card) {
        super(card);
    }

    @Override
    public WillowGeist copy() {
        return new WillowGeist(this);
    }
}
