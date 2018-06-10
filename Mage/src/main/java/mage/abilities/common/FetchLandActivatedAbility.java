

package mage.abilities.common;

import mage.MageObject;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FetchLandActivatedAbility extends ActivatedAbilityImpl {

    public FetchLandActivatedAbility(Set<SubType> subtypes) {
        this(true, subtypes);
    }

    public FetchLandActivatedAbility(boolean withDamage, Set<SubType> subtypes) {
        super(Zone.BATTLEFIELD, null);
        addCost(new TapSourceCost());
        if (withDamage) {
            addCost(new PayLifeCost(1));
        }
        addCost(new SacrificeSourceCost());
        FilterCard filter = new FilterCard(subTypeNames(subtypes));
        filter.add(new CardTypePredicate(CardType.LAND));
        List<Predicate<MageObject>> subtypePredicates = new ArrayList<>();
        for (SubType subtype : subtypes) {
            subtypePredicates.add(new SubtypePredicate(subtype));
        }
        filter.add(Predicates.or(subtypePredicates));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        addEffect(new SearchLibraryPutInPlayEffect(target, false, true, Outcome.PutLandInPlay));
    }

    public FetchLandActivatedAbility(FetchLandActivatedAbility ability) {
        super(ability);
    }

    private String subTypeNames(Set<SubType> subTypes) {
        StringBuilder sb = new StringBuilder();
        for (SubType subType: subTypes) {
            sb.append(subType.getDescription()).append(" or ");
        }
        return sb.substring(0, sb.length() - 4);
    }

    @Override
    public FetchLandActivatedAbility copy() {
        return new FetchLandActivatedAbility(this);
    }
}
