
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
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
public final class NimanaSellSword extends CardImpl {

    public NimanaSellSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true).setAbilityWord(null));
    }

    private NimanaSellSword(final NimanaSellSword card) {
        super(card);
    }

    @Override
    public NimanaSellSword copy() {
        return new NimanaSellSword(this);
    }
}
