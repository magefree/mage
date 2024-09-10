
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author L_J
 */
public final class ElendaTheDuskRose extends CardImpl {

    public ElendaTheDuskRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another creature dies, put a +1/+1 counter on Elenda, The Dusk Rose.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));

        // When Elenda dies, create X 1/1 white Vampire creature tokens with lifelink, where X is Elenda's power. 
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new IxalanVampireToken(), new SourcePermanentPowerCount())));
    }

    private ElendaTheDuskRose(final ElendaTheDuskRose card) {
        super(card);
    }

    @Override
    public ElendaTheDuskRose copy() {
        return new ElendaTheDuskRose(this);
    }
}
