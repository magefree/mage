package mage.cards.t;

import java.util.UUID;
import mage.constants.SuperType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DrawCardsEqualToDifferenceEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheTenRings extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 10);

    public TheTenRings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{8}");

        this.supertype.add(SuperType.LEGENDARY);

        // Your maximum hand size is ten.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
            10, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // At the beginning of your end step, if you have fewer than ten cards in hand, draw cards equal to the difference.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardsEqualToDifferenceEffect(10)).withInterveningIf(condition));
    }

    private TheTenRings(final TheTenRings card) {
        super(card);
    }

    @Override
    public TheTenRings copy() {
        return new TheTenRings(this);
    }
}
