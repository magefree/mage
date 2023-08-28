
package mage.cards.j;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class JungleVillage extends CardImpl {

    public JungleVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Add {C} to your manapool.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Jungle Village: Search your library for a basic Mountain, Forest or Plains card and put it onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new JungleVillageAbility());

    }

    private JungleVillage(final JungleVillage card) {
        super(card);
    }

    @Override
    public JungleVillage copy() {
        return new JungleVillage(this);
    }
}


class JungleVillageAbility extends ActivatedAbilityImpl {

    private static final FilterCard filter = new FilterCard("basic Mountain, Forest or Plains");

    static {
        filter.add(CardType.LAND.getPredicate());
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
        filter.add(SuperType.BASIC.getPredicate());
    }

    private JungleVillageAbility(final JungleVillageAbility ability) {
        super(ability);
    }

    public JungleVillageAbility() {
        super(Zone.BATTLEFIELD, null);
        addCost(new TapSourceCost());
        addCost(new SacrificeSourceCost());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        addEffect(new SearchLibraryPutInPlayEffect(target, true));
    }

    @Override
    public JungleVillageAbility copy() {
        return new JungleVillageAbility(this);
    }
}