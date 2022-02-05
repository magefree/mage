package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyojinOfBloomingDawn extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT);
    private static final Hint hint = new ValueHint("Permanents you control", xValue);

    public MyojinOfBloomingDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Myojin of Blooming Dawn enters the battlefield with an indestructible counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()),
                CastFromHandSourcePermanentCondition.instance, ""
        ), "{this} enters the battlefield with an indestructible counter on it if you cast it from your hand"));

        // Remove an indestructible counter from Myojin of Blooming Dawn: Create a 1/1 colorless Spirit creature token for each permanent you control.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new SpiritToken(), xValue),
                new RemoveCountersSourceCost(CounterType.INDESTRUCTIBLE.createInstance())
        ).addHint(hint));
    }

    private MyojinOfBloomingDawn(final MyojinOfBloomingDawn card) {
        super(card);
    }

    @Override
    public MyojinOfBloomingDawn copy() {
        return new MyojinOfBloomingDawn(this);
    }
}
