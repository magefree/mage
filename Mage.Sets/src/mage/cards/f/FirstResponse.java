
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.LiveLostLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author LevelX2
 */
public final class FirstResponse extends CardImpl {

    public FirstResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // At the beginning of each upkeep, if you lost life last turn, create a 1/1 white Soldier creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SoldierToken()), TargetController.ANY, false),
                LiveLostLastTurnCondition.instance,
                "At the beginning of each upkeep, if you lost life last turn, create a 1/1 white Soldier creature token."));
    }

    private FirstResponse(final FirstResponse card) {
        super(card);
    }

    @Override
    public FirstResponse copy() {
        return new FirstResponse(this);
    }
}
