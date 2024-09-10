package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StalwartsOfOsgiliath extends CardImpl {

    public StalwartsOfOsgiliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Stalwarts of Osgiliath enters the battlefield, the Ring tempts you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheRingTemptsYouEffect()));

        // Whenever you draw your second card each turn, put a +1/+1 counter on Stalwarts of Osgiliath.
        this.addAbility(new DrawNthCardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, 2
        ));
    }

    private StalwartsOfOsgiliath(final StalwartsOfOsgiliath card) {
        super(card);
    }

    @Override
    public StalwartsOfOsgiliath copy() {
        return new StalwartsOfOsgiliath(this);
    }
}
