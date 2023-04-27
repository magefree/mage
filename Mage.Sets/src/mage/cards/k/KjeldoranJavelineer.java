
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author TheElk801
 */
public final class KjeldoranJavelineer extends CardImpl {

    public KjeldoranJavelineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // {T}: Kjeldoran Javelineer deals damage equal to the number of age counters on it to target attacking or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(new CountersSourceCount(CounterType.AGE))
                        .setText("{this} deals damage equal to the number of age counters on it to target attacking or blocking creature."),
                 new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(ability);
    }

    private KjeldoranJavelineer(final KjeldoranJavelineer card) {
        super(card);
    }

    @Override
    public KjeldoranJavelineer copy() {
        return new KjeldoranJavelineer(this);
    }
}
