package mage.cards.s;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
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

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 2, TargetController.ACTIVE);

    public ShriekingAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // At the beginning of each opponent's upkeep, if that player has one or fewer cards in hand, they lose 3 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, TargetController.OPPONENT, new LoseLifeTargetEffect(3).setText("they lose 3 life"), false
        ).withInterveningIf(condition));
    }

    private ShriekingAffliction(final ShriekingAffliction card) {
        super(card);
    }

    @Override
    public ShriekingAffliction copy() {
        return new ShriekingAffliction(this);
    }
}
