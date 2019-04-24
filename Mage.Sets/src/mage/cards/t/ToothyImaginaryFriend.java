
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class ToothyImaginaryFriend extends CardImpl {

    public ToothyImaginaryFriend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Partner with Pir, Imaginative Rascal (When this creature enters the battlefield, target player may put Pir into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Pir, Imaginative Rascal", true));

        // Whenever you draw a card, put a +1/+1 counter on Toothy, Imaginary Friend.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));

        // When Toothy leaves the battlefield, draw a card for each +1/+1 counter on it.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.P1P1))
                        .setText("draw a card for each +1/+1 counter on it"), false));
    }

    public ToothyImaginaryFriend(final ToothyImaginaryFriend card) {
        super(card);
    }

    @Override
    public ToothyImaginaryFriend copy() {
        return new ToothyImaginaryFriend(this);
    }
}
