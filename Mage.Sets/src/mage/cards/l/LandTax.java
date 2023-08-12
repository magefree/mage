
package mage.cards.l;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class LandTax extends CardImpl {

    public LandTax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // At the beginning of your upkeep, if an opponent controls more lands than you, you may search your library for up to three basic land cards, reveal them, and put them into your hand. If you do, shuffle your library.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_BASIC_LAND), true), TargetController.YOU, true),
                new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS),
                "At the beginning of your upkeep, if an opponent controls more lands than you, you may search your library for up to three basic land cards, reveal them, put them into your hand, then shuffle."
        ));

    }

    private LandTax(final LandTax card) {
        super(card);
    }

    @Override
    public LandTax copy() {
        return new LandTax(this);
    }
}
