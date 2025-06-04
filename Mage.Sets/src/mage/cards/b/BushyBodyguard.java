package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BushyBodyguard extends CardImpl {

    public BushyBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Offspring {2}
        this.addAbility(new OffspringAbility("{2}"));

        // When this creature enters, you may forage. If you do, put two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).setText("put two +1/+1 counters on it"), new ForageCost()
        )));
    }

    private BushyBodyguard(final BushyBodyguard card) {
        super(card);
    }

    @Override
    public BushyBodyguard copy() {
        return new BushyBodyguard(this);
    }
}
