package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Backfir3
 */
public final class CateranSlaver extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Mercenary permanent card with mana value 5 or less");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public CateranSlaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // {5}, {T}: Search your library for a Mercenary permanent card with converted mana cost 5 or less and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addCost(new GenericManaCost(5));
        this.addAbility(ability);
    }

    private CateranSlaver(final CateranSlaver card) {
        super(card);
    }

    @Override
    public CateranSlaver copy() {
        return new CateranSlaver(this);
    }
}
