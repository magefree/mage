package mage.cards.r;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.LifeCompareCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazorPendulum extends CardImpl {

    private static final Condition condition = new LifeCompareCondition(TargetController.ACTIVE, ComparisonType.OR_LESS, 5);

    public RazorPendulum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each player’s end step, if that player has 5 or less life, Razor Pendulum deals 2 damage to that player.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.EACH_PLAYER,
                new DamageTargetEffect(2, true, "that player"),
                false, condition
        ));
    }

    private RazorPendulum(final RazorPendulum card) {
        super(card);
    }

    @Override
    public RazorPendulum copy() {
        return new RazorPendulum(this);
    }
}
