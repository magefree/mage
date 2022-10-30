package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class EnchantedPlayerAttackedTriggeredAbility extends TriggeredAbilityImpl {

    public EnchantedPlayerAttackedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever enchanted player is attacked, ");
    }

    public EnchantedPlayerAttackedTriggeredAbility(final EnchantedPlayerAttackedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(getSourceId());
        return enchantment != null
                && game.getCombat().getPlayerDefenders(game, false).contains(enchantment.getAttachedTo());
    }

    @Override
    public EnchantedPlayerAttackedTriggeredAbility copy() {
        return new EnchantedPlayerAttackedTriggeredAbility(this);
    }

}
