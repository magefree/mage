package mage.cards.g;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.permanent.token.GriffinToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GriffinAerie extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition, "You gained 3 or more life this turn");

    public GriffinAerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your end step, if you gained 3 or more life this turn, create a 2/2 white Griffin creature token with flying.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new GriffinToken()), TargetController.YOU, false
                ), condition, "At the beginning of your end step, " +
                "if you gained 3 or more life this turn, create a 2/2 white Griffin creature token with flying."
        ).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private GriffinAerie(final GriffinAerie card) {
        super(card);
    }

    @Override
    public GriffinAerie copy() {
        return new GriffinAerie(this);
    }
}
