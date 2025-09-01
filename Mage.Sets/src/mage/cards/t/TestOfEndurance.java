package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TestOfEndurance extends CardImpl {

    private static final Condition condition = new LifeCompareCondition(TargetController.YOU, ComparisonType.OR_GREATER, 50);

    public TestOfEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, if you have 50 or more life, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(condition));
    }

    private TestOfEndurance(final TestOfEndurance card) {
        super(card);
    }

    @Override
    public TestOfEndurance copy() {
        return new TestOfEndurance(this);
    }
}
