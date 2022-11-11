package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Luna Skyrise
 */
public final class CityOfShadows extends CardImpl {

    public CityOfShadows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}, Exile a creature you control: Put a storage counter on City of Shadows.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.STORAGE.createInstance()), new TapSourceCost());
        ability.addCost(new ExileTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_A_CREATURE)));
        this.addAbility(ability);

        // {T}: Add {C} for each storage counter on City of Shadows.
        ability = new DynamicManaAbility(Mana.ColorlessMana(1), new CountersSourceCount(CounterType.STORAGE),
                "Add {C} for each storage counter on {this}");
        this.addAbility(ability);
    }

    private CityOfShadows(final CityOfShadows card) {
        super(card);
    }

    @Override
    public CityOfShadows copy() {
        return new CityOfShadows(this);
    }
}
