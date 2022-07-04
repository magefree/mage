
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class KarlovOfTheGhostCouncil extends CardImpl {

    public KarlovOfTheGhostCouncil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you gain life, put two +1/+1 counter on Karlov of the Ghost Council.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false));
        
        // {W}{B}, Remove six +1/+1 counters from Karlov of the Ghost Council: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{W}{B}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(6)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KarlovOfTheGhostCouncil(final KarlovOfTheGhostCouncil card) {
        super(card);
    }

    @Override
    public KarlovOfTheGhostCouncil copy() {
        return new KarlovOfTheGhostCouncil(this);
    }
}
