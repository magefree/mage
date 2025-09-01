package mage.cards.b;

import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BarrenGlory extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            "you control no permanents other than this enchantment and have no cards in hand",
            new CardsInHandCondition(ComparisonType.EQUAL_TO, 0),
            new PermanentsOnTheBattlefieldCondition(
                    StaticFilters.FILTER_OTHER_CONTROLLED_PERMANENTS, ComparisonType.EQUAL_TO, 0
            )
    );

    public BarrenGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // At the beginning of your upkeep, if you control no permanents other than Barren Glory and have no cards in hand, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(condition));
    }

    private BarrenGlory(final BarrenGlory card) {
        super(card);
    }

    @Override
    public BarrenGlory copy() {
        return new BarrenGlory(this);
    }
}
