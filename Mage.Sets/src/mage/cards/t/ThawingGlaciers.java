
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextCleanupDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ThawingGlaciers extends CardImpl {

    public ThawingGlaciers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Thawing Glaciers enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {1}, {tap}: Search your library for a basic land card, put that card onto the battlefield tapped, then shuffle your library. Return Thawing Glaciers to its owner's hand at the beginning of the next cleanup step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextCleanupDelayedTriggeredAbility(new ReturnToHandSourceEffect(true))));

        this.addAbility(ability);
    }

    private ThawingGlaciers(final ThawingGlaciers card) {
        super(card);
    }

    @Override
    public ThawingGlaciers copy() {
        return new ThawingGlaciers(this);
    }
}
