

package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
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
public final class JourneyersKite extends CardImpl {


        public JourneyersKite (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {3}, {tap}: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private JourneyersKite(final JourneyersKite card) {
        super(card);
    }

    @Override
    public JourneyersKite copy() {
        return new JourneyersKite(this);
    }

}
