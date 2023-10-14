
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
@SuppressWarnings("unchecked")
public final class NayaPanorama extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Mountain, Forest, or Plains card");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()));
    }

    public NayaPanorama(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(target, true), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private NayaPanorama(final NayaPanorama card) {
        super(card);
    }

    @Override
    public NayaPanorama copy() {
        return new NayaPanorama(this);
    }
}
