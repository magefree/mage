package mage.abilities.abilityword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */

public class EerieAbility extends TriggeredAbilityImpl {

    public EerieAbility(Effect effect) {
        this(effect, false);
    }

    public EerieAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public EerieAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setAbilityWord(AbilityWord.EERIE);
        setTriggerPhrase("Whenever an enchantment you control enters and whenever you fully unlock a Room, ");
    }

    protected EerieAbility(final EerieAbility ability) {
        super(ability);
    }

    @Override
    public EerieAbility copy() {
        return new EerieAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ROOM_FULLY_UNLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            if (!isControlledBy(event.getPlayerId())) {
                return false;
            }
            Permanent permanent = game.getPermanent(event.getTargetId());
            return permanent != null && permanent.isEnchantment(game);
        }

        if (event.getType() == GameEvent.EventType.ROOM_FULLY_UNLOCKED) {
            return isControlledBy(event.getPlayerId());
        }
        return false;
    }
}
