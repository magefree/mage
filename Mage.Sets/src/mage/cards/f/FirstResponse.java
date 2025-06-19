package mage.cards.f;

import mage.abilities.condition.common.LiveLostLastTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FirstResponse extends CardImpl {

    public FirstResponse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // At the beginning of each upkeep, if you lost life last turn, create a 1/1 white Soldier creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new SoldierToken()), false
        ).withInterveningIf(LiveLostLastTurnCondition.instance));
    }

    private FirstResponse(final FirstResponse card) {
        super(card);
    }

    @Override
    public FirstResponse copy() {
        return new FirstResponse(this);
    }
}
