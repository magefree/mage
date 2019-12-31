package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ReverentHunter extends CardImpl {

    public ReverentHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Reverent Hunter enters the battlefield, put a number of +1/+1 counters on it equal to your devotion to green.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), DevotionCount.G, true
        )).addHint(DevotionCount.G.getHint()));
    }

    private ReverentHunter(final ReverentHunter card) {
        super(card);
    }

    @Override
    public ReverentHunter copy() {
        return new ReverentHunter(this);
    }
}
