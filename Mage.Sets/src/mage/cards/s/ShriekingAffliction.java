package mage.cards.s;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */

public final class ShriekingAffliction extends CardImpl {

    public ShriekingAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, they lose 3 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.BATTLEFIELD, new LoseLifeTargetEffect(3),
                        TargetController.OPPONENT, false, true
                ),
                new CardsInHandCondition(ComparisonType.FEWER_THAN, 2, null, TargetController.ACTIVE),
                "At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, they lose 3 life."
        ));
    }

    private ShriekingAffliction(final ShriekingAffliction card) {
        super(card);
    }

    @Override
    public ShriekingAffliction copy() {
        return new ShriekingAffliction(this);
    }
}
