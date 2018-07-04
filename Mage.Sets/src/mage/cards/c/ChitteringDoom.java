
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author spjspj
 */
public final class ChitteringDoom extends CardImpl {

    public ChitteringDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever you roll a 4 or higher on a die, create a 1/1 green Squirrel creature token.
        this.addAbility(new ChitteringDoomTriggeredAbility());
    }

    public ChitteringDoom(final ChitteringDoom card) {
        super(card);
    }

    @Override
    public ChitteringDoom copy() {
        return new ChitteringDoom(this);
    }
}

class ChitteringDoomTriggeredAbility extends TriggeredAbilityImpl {

    public ChitteringDoomTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SquirrelToken()), false);
    }

    public ChitteringDoomTriggeredAbility(final ChitteringDoomTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChitteringDoomTriggeredAbility copy() {
        return new ChitteringDoomTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DICE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (this.isControlledBy(event.getPlayerId()) && event.getFlag()) {
            if (event.getAmount() >= 4) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 4 or higher on a die, create a 1/1 green Squirrel creature token";
    }
}
