
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class NayaSojourners extends CardImpl {

    public NayaSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);




        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When you cycle Naya Sojourners or it dies, you may put a +1/+1 counter on target creature.
        Ability ability1 = new CycleTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        Ability ability2 = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability1.addTarget(new TargetCreaturePermanent());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{G}")));
    }

    private NayaSojourners(final NayaSojourners card) {
        super(card);
    }

    @Override
    public NayaSojourners copy() {
        return new NayaSojourners(this);
    }
}
