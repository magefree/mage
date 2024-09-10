package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorskaUnderseaSleuth extends CardImpl {

    public MorskaUnderseaSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // At the beginning of your upkeep, investigate.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new InvestigateEffect(), TargetController.YOU, false
        ));

        // Whenever you draw your second card each turn, put two +1/+1 counters on Morska, Undersea Sleuth.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false, 2
        ));
    }

    private MorskaUnderseaSleuth(final MorskaUnderseaSleuth card) {
        super(card);
    }

    @Override
    public MorskaUnderseaSleuth copy() {
        return new MorskaUnderseaSleuth(this);
    }
}
