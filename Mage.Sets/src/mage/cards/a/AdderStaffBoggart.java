
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class AdderStaffBoggart extends CardImpl {

    public AdderStaffBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Adder-Staff Boggart enters the battlefield, clash with an opponent. If you win, put a +1/+1 counter on Adder-Staff Boggart.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfClashWonEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))));
    }

    private AdderStaffBoggart(final AdderStaffBoggart card) {
        super(card);
    }

    @Override
    public AdderStaffBoggart copy() {
        return new AdderStaffBoggart(this);
    }
}
