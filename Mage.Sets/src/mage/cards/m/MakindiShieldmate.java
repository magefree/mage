
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class MakindiShieldmate extends CardImpl {

    public MakindiShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true).setAbilityWord(null));
    }

    private MakindiShieldmate(final MakindiShieldmate card) {
        super(card);
    }

    @Override
    public MakindiShieldmate copy() {
        return new MakindiShieldmate(this);
    }
}
