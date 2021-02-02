
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
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
public final class RubblebeltRaiders extends CardImpl {

    public RubblebeltRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R/G}{R/G}{R/G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Rubblebelt Raiders attacks, put a +1/+1 counter on it for each attacking creature you control.
        this.addAbility(new AttacksTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new AttackingCreatureCount("attacking creature you control"), true),false));
    }

    private RubblebeltRaiders(final RubblebeltRaiders card) {
        super(card);
    }

    @Override
    public RubblebeltRaiders copy() {
        return new RubblebeltRaiders(this);
    }
}
