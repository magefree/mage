
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author North
 */
public final class UmaraRaptor extends CardImpl {

    public UmaraRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true).setAbilityWord(null));
    }

    private UmaraRaptor(final UmaraRaptor card) {
        super(card);
    }

    @Override
    public UmaraRaptor copy() {
        return new UmaraRaptor(this);
    }
}
