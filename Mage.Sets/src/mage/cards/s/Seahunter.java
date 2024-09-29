
package mage.cards.s;

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
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Backfir3
 */
public final class Seahunter extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Merfolk permanent card");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public Seahunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}, {T}: Search your library for a Merfolk permanent card and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        ability.addCost(new GenericManaCost(3));
        this.addAbility(ability);
    }

    private Seahunter(final Seahunter card) {
        super(card);
    }

    @Override
    public Seahunter copy() {
        return new Seahunter(this);
    }

}
