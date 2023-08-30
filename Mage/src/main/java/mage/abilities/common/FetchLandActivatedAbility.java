

package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class FetchLandActivatedAbility extends ActivatedAbilityImpl {

    public FetchLandActivatedAbility(SubType subType1, SubType subType2) {
        this(true, subType1, subType2);
    }

    public FetchLandActivatedAbility(boolean withDamage, SubType subType1, SubType subType2) {
        super(Zone.BATTLEFIELD, null);
        addCost(new TapSourceCost());
        if (withDamage) {
            addCost(new PayLifeCost(1));
        }
        addCost(new SacrificeSourceCost());
        FilterCard filter = new FilterCard(subType1.getDescription() + " or " + subType2.getDescription() + " card");
        filter.add(Predicates.or(subType1.getPredicate(), subType2.getPredicate()));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        addEffect(new SearchLibraryPutInPlayEffect(target, false));
    }

    private FetchLandActivatedAbility(FetchLandActivatedAbility ability) {
        super(ability);
    }

    @Override
    public FetchLandActivatedAbility copy() {
        return new FetchLandActivatedAbility(this);
    }
}
