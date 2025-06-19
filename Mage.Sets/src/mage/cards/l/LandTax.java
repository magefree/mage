package mage.cards.l;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LandTax extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);

    public LandTax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // At the beginning of your upkeep, if an opponent controls more lands than you, you may search your library for up to three basic land cards, reveal them, and put them into your hand. If you do, shuffle your library.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        ), true).withInterveningIf(condition));
    }

    private LandTax(final LandTax card) {
        super(card);
    }

    @Override
    public LandTax copy() {
        return new LandTax(this);
    }
}
