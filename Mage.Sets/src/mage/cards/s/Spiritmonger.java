
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class Spiritmonger extends CardImpl {

    public Spiritmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Spiritmonger deals damage to a creature, put a +1/+1 counter on Spiritmonger.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), false, false, false));
        // {B}: Regenerate Spiritmonger.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
        // {G}: Spiritmonger becomes the color of your choice until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesColorSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{G}")));
    }

    private Spiritmonger(final Spiritmonger card) {
        super(card);
    }

    @Override
    public Spiritmonger copy() {
        return new Spiritmonger(this);
    }
}
