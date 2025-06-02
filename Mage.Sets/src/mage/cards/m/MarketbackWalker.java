package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarketbackWalker extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public MarketbackWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}{X}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // This creature enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // {4}: Put a +1/+1 counter on this creature.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(4)
        ));

        // When this creature dies, draw a card for each +1/+1 counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(xValue).setText("draw a card for each +1/+1 counter on it")));
    }

    private MarketbackWalker(final MarketbackWalker card) {
        super(card);
    }

    @Override
    public MarketbackWalker copy() {
        return new MarketbackWalker(this);
    }
}
