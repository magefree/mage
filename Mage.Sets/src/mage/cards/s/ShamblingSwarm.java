
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LoneFox
 */
public final class ShamblingSwarm extends CardImpl {

    public ShamblingSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Shambling Swarm dies, distribute three -1/-1 counters among one, two, or three target creatures. For each -1/-1 counter you put on a creature this way, remove a -1/-1 counter from that creature at the beginning of the next end step.
        Ability ability = new DiesSourceTriggeredAbility(new DistributeCountersEffect(CounterType.M1M1, 3, true, "one, two, or three target creatures"), false);
        ability.addTarget(new TargetCreaturePermanentAmount(3));
        this.addAbility(ability);
    }

    private ShamblingSwarm(final ShamblingSwarm card) {
        super(card);
    }

    @Override
    public ShamblingSwarm copy() {
        return new ShamblingSwarm(this);
    }
}
