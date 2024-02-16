package mage.cards.g;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author weirddan455
 */
public final class GlitteringStockpile extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.STASH);

    public GlitteringStockpile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.TREASURE);

        // {T}: Add {R}. Put a stash counter on Glittering Stockpile.
        Ability ability = new RedManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.STASH.createInstance()));
        this.addAbility(ability);

        // {T}, Sacrifice Glittering Stockpile: Add X mana of any one color, where X is the number of stash counters on Glittering Stockpile.
        ability = new DynamicManaAbility(
                new Mana(0, 0, 0, 0, 0, 0, 1, 0),
                xValue, new TapSourceCost(), "Add X mana of any one color, where X is the number of stash counters on {this}", true
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GlitteringStockpile(final GlitteringStockpile card) {
        super(card);
    }

    @Override
    public GlitteringStockpile copy() {
        return new GlitteringStockpile(this);
    }
}
