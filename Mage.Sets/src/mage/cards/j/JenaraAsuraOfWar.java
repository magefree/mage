

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class JenaraAsuraOfWar extends CardImpl {

    public JenaraAsuraOfWar (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}{U}");
        this.subtype.add(SubType.ANGEL);
        this.supertype.add(SuperType.LEGENDARY);


        
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{W}")));
    }

    public JenaraAsuraOfWar (final JenaraAsuraOfWar card) {
        super(card);
    }

    @Override
    public JenaraAsuraOfWar copy() {
        return new JenaraAsuraOfWar(this);
    }

}
