package mage.cards.p;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
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
public final class PaupersCage extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 3, TargetController.ACTIVE);

    public PaupersCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Paupers' Cage deals 2 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT, new DamageTargetEffect(2, true, "that player"), false
        ).withInterveningIf(condition));
    }

    private PaupersCage(final PaupersCage card) {
        super(card);
    }

    @Override
    public PaupersCage copy() {
        return new PaupersCage(this);
    }
}
