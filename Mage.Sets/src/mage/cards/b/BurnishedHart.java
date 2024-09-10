
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class BurnishedHart extends CardImpl {

    public BurnishedHart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}, Sacrifice Burnished Hart: Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0,2, StaticFilters.FILTER_CARD_BASIC_LANDS), true),
                new GenericManaCost(3));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BurnishedHart(final BurnishedHart card) {
        super(card);
    }

    @Override
    public BurnishedHart copy() {
        return new BurnishedHart(this);
    }
}
