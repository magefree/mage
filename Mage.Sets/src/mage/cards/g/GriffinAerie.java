package mage.cards.g;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.game.permanent.token.GriffinToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GriffinAerie extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);

    public GriffinAerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of your end step, if you gained 3 or more life this turn, create a 2/2 white Griffin creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new GriffinToken()))
                .withInterveningIf(condition).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private GriffinAerie(final GriffinAerie card) {
        super(card);
    }

    @Override
    public GriffinAerie copy() {
        return new GriffinAerie(this);
    }
}
