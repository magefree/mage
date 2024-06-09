package mage.cards.n;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NearDeathExperience extends CardImpl {

    public NearDeathExperience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}{W}");

        // At the beginning of your upkeep, if you have exactly 1 life, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false),
                new LifeCompareCondition(TargetController.YOU, ComparisonType.EQUAL_TO, 1),
                "At the beginning of your upkeep, if you have exactly 1 life, you win the game."));
    }

    private NearDeathExperience(final NearDeathExperience card) {
        super(card);
    }

    @Override
    public NearDeathExperience copy() {
        return new NearDeathExperience(this);
    }
}
