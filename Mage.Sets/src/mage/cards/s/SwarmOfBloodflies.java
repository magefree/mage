
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class SwarmOfBloodflies extends CardImpl {

    public SwarmOfBloodflies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Swarm of Bloodflies enters the battlefield with two +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(2), true);
        effect.setText("with two +1/+1 counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // Whenever another creature dies, put a +1/+1 counter on Swarm of Bloodflies
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));
    }

    private SwarmOfBloodflies(final SwarmOfBloodflies card) {
        super(card);
    }

    @Override
    public SwarmOfBloodflies copy() {
        return new SwarmOfBloodflies(this);
    }
}
