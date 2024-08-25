package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.ValiantTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeartfireHero extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public HeartfireHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Valiant -- Whenever Heartfire Hero becomes the target of a spell or ability you control for the first time each turn, put a +1/+1 counter on it.
        this.addAbility(new ValiantTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on it")));

        // When Heartfire Hero dies, it deals damage equal to its power to each opponent.
        this.addAbility(new DiesSourceTriggeredAbility(new DamagePlayersEffect(xValue, TargetController.OPPONENT)
                .setText("it deals damage equal to its power to each opponent")));
    }

    private HeartfireHero(final HeartfireHero card) {
        super(card);
    }

    @Override
    public HeartfireHero copy() {
        return new HeartfireHero(this);
    }
}
