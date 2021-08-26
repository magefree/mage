
package mage.cards.c;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class ChitteringDoom extends CardImpl {

    public ChitteringDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever you roll a 4 or higher on a die, create a 1/1 green Squirrel creature token.
        this.addAbility(new ChitteringDoomTriggeredAbility());
    }

    private ChitteringDoom(final ChitteringDoom card) {
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
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        // silver border card must look for "result" instead "natural result"
        return this.isControlledBy(event.getPlayerId()) && drEvent.getResult() >= 4;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 4 or higher on a die, create a 1/1 green Squirrel creature token";
    }
}
