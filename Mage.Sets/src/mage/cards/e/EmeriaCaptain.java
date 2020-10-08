package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmeriaCaptain extends CardImpl {

    public EmeriaCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Emeria Captain enters the battlefield, put a +1/+1 counter on it for each creature in your party.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), PartyCount.instance, false
        ).setText("put a +1/+1 counter on it for each creature in your party. " + PartyCount.getReminder())));
    }

    private EmeriaCaptain(final EmeriaCaptain card) {
        super(card);
    }

    @Override
    public EmeriaCaptain copy() {
        return new EmeriaCaptain(this);
    }
}
