package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class GhirapurOrrery extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0, TargetController.ACTIVE);

    public GhirapurOrrery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Each player may play an additional land on each of their turns.
        this.addAbility(new SimpleStaticAbility(new PlayAdditionalLandsAllEffect()));

        // At the beginning of each player's upkeep, if that player has no cards in hand, that player draws three cards.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new DrawCardTargetEffect(3), false
        ).withInterveningIf(condition));
    }

    private GhirapurOrrery(final GhirapurOrrery card) {
        super(card);
    }

    @Override
    public GhirapurOrrery copy() {
        return new GhirapurOrrery(this);
    }
}
