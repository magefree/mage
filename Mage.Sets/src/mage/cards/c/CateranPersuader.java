

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
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
public final class CateranPersuader extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Mercenary permanent card with mana value 1 or less");

    static {
        filter.add(SubType.MERCENARY.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public CateranPersuader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}, {T}: Search your library for a Mercenary permanent card with converted mana cost 1 or less and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addCost(new GenericManaCost(1));
        this.addAbility(ability);
    }

    private CateranPersuader(final CateranPersuader card) {
        super(card);
    }

    @Override
    public CateranPersuader copy() {
        return new CateranPersuader(this);
    }
}
