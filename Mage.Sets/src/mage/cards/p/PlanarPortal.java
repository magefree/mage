
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class PlanarPortal extends CardImpl {

    public PlanarPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {6}, {tap}: Search your library for a card and put that card into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false, true), new ManaCostsImpl<>("{6}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private PlanarPortal(final PlanarPortal card) {
        super(card);
    }

    @Override
    public PlanarPortal copy() {
        return new PlanarPortal(this);
    }
}
