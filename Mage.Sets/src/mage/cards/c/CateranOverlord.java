
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class CateranOverlord extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Mercenary permanent card with mana value 6 or less");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 7));
    }

    public CateranOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Sacrifice a creature: Regenerate Cateran Overlord.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // {6}, {T}: Search your library for a Mercenary permanent card with converted mana cost 6 or less and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addManaCost(new GenericManaCost(6));
        this.addAbility(ability);
    }

    private CateranOverlord(final CateranOverlord card) {
        super(card);
    }

    @Override
    public CateranOverlord copy() {
        return new CateranOverlord(this);
    }
}
