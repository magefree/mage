package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author balazskristof
 */
public final class CookingCampsite extends CardImpl {

    public CookingCampsite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.nightCard = true;

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {3}, {T}, Sacrifice an artifact: Put a +1/+1 counter on each creature you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE), new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private CookingCampsite(final CookingCampsite card) {
        super(card);
    }

    @Override
    public CookingCampsite copy() {
        return new CookingCampsite(this);
    }
}
