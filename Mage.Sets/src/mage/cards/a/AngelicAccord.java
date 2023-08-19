package mage.cards.a;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.AngelToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class AngelicAccord extends CardImpl {

    public AngelicAccord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // At the beginning of each end step, if you gained 4 or more life this turn, create a 4/4 white Angel creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()), TargetController.ANY,
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 3), false
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private AngelicAccord(final AngelicAccord card) {
        super(card);
    }

    @Override
    public AngelicAccord copy() {
        return new AngelicAccord(this);
    }
}
