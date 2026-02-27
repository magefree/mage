package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphsBravado extends CardImpl {

    public RaphsBravado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // During your turn, attacking creatures get +1/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new BoostAllEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES, false
        ), MyTurnCondition.instance, "uring your turn, attacking creatures get +1/+0")));
    }

    private RaphsBravado(final RaphsBravado card) {
        super(card);
    }

    @Override
    public RaphsBravado copy() {
        return new RaphsBravado(this);
    }
}
