
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class HeroesBane extends CardImpl {

    public HeroesBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Heroe's Bane enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4), true), 
                "with four +1/+1 counters on it"));
        // {2}{G}{G}: Put X +1/+1 counters on Heroe's Bane, where X is its power.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new SourcePermanentPowerCount(), true);
        effect.setText("Put X +1/+1 counters on {this}, where X is its power");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{G}{G}")));
    }

    private HeroesBane(final HeroesBane card) {
        super(card);
    }

    @Override
    public HeroesBane copy() {
        return new HeroesBane(this);
    }
}
