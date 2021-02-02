
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class SpinalParasite extends CardImpl {

    public SpinalParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(-1);
        this.toughness = new MageInt(-1);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Remove two +1/+1 counters from Spinal Parasite: Remove a counter from target permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RemoveCounterTargetEffect(),
            new RemoveCountersSourceCost(CounterType.P1P1.createInstance(2)));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private SpinalParasite(final SpinalParasite card) {
        super(card);
    }

    @Override
    public SpinalParasite copy() {
        return new SpinalParasite(this);
    }
}
