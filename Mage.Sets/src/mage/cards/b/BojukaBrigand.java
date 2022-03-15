
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class BojukaBrigand extends CardImpl {

    public BojukaBrigand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR, SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new CantBlockAbility());
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true).setAbilityWord(null));
    }

    private BojukaBrigand(final BojukaBrigand card) {
        super(card);
    }

    @Override
    public BojukaBrigand copy() {
        return new BojukaBrigand(this);
    }
}
