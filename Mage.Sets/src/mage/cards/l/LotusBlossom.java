
package mage.cards.l;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 * @author JRHerlehy
 */
public final class LotusBlossom extends CardImpl {

    public LotusBlossom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");


        // At the beginning of your upkeep, you may put a petal counter on Lotus Blossom.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.PETAL.createInstance(), true), TargetController.YOU, true));
        // {tap}, Sacrifice Lotus Blossom: Add X mana of any one color, where X is the number of petal counters on Lotus Blossom.
        DynamicManaAbility ability = new DynamicManaAbility(new Mana(0, 0, 0, 0, 0, 0, 1, 0), new CountersSourceCount(CounterType.PETAL), new TapSourceCost(),
                "Add X mana of any one color, where X is the number of petal counters on {this}", true);
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private LotusBlossom(final LotusBlossom card) {
        super(card);
    }

    @Override
    public LotusBlossom copy() {
        return new LotusBlossom(this);
    }
}
