
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class WildFieldScarecrow extends CardImpl {

    public WildFieldScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}, Sacrifice Wild-Field Scarecrow: Search your library for up to two basic land cards, reveal them, and put them into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true),
                new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private WildFieldScarecrow(final WildFieldScarecrow card) {
        super(card);
    }

    @Override
    public WildFieldScarecrow copy() {
        return new WildFieldScarecrow(this);
    }
}
