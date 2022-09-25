package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

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
        Player controller = game.getPlayer(getControllerId());
        Player attacker = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (controller == null || attacker == null || enchantment == null) {
            return false;
        }

        Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
        if (enchantedPlayer == null) {
            return false;
        }

        Set<UUID> opponentIds = game.getOpponents(controller.getId());
        if (!opponentIds.contains(attacker.getId()) || !opponentIds.contains(enchantedPlayer.getId())) {
            return false;
        }

        return game.getCombat().getPlayerDefenders(game, false).contains(enchantment.getAttachedTo());
    }

    @Override
    public EnchantedPlayerAttackedTriggeredAbility copy() {
        return new EnchantedPlayerAttackedTriggeredAbility(this);
    }

}
