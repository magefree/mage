
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author anonymous
 */
public final class PlanarBridge extends CardImpl {

    public PlanarBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);

        // {8}, {T}: Search your library for a permanent card, put it onto the battlefield, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterPermanentCard())),
                new GenericManaCost(8)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private PlanarBridge(final PlanarBridge card) {
        super(card);
    }

    @Override
    public PlanarBridge copy() {
        return new PlanarBridge(this);
    }
}
