
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class SurvivalOfTheFittest extends CardImpl {

    public SurvivalOfTheFittest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // {G}, Discard a creature card: Search your library for a creature card, reveal that card, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), true, true), new ColoredManaCost(ColoredManaSymbol.G));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE)));
        this.addAbility(ability);
    }

    private SurvivalOfTheFittest(final SurvivalOfTheFittest card) {
        super(card);
    }

    @Override
    public SurvivalOfTheFittest copy() {
        return new SurvivalOfTheFittest(this);
    }
}
