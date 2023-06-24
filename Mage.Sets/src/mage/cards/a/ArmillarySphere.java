
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class ArmillarySphere extends CardImpl {

    public ArmillarySphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}, {tap}, Sacrifice Armillary Sphere: Search your library for up to two basic land cards, reveal them, and put them into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true),
                new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ArmillarySphere(final ArmillarySphere card) {
        super(card);
    }

    @Override
    public ArmillarySphere copy() {
        return new ArmillarySphere(this);
    }
}
