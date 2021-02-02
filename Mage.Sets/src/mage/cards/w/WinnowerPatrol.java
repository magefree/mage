
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
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
public final class WinnowerPatrol extends CardImpl {

    public WinnowerPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Winnower Patrol, you may reveal it. 
        // If you do, put a +1/+1 counter on Winnower Patrol.
        this.addAbility(new KinshipAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private WinnowerPatrol(final WinnowerPatrol card) {
        super(card);
    }

    @Override
    public WinnowerPatrol copy() {
        return new WinnowerPatrol(this);
    }
}
