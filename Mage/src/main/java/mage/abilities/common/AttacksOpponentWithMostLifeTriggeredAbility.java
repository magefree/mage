package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.Objects;

/**
 * @author TheElk801
 */
public class AttacksOpponentWithMostLifeTriggeredAbility extends TriggeredAbilityImpl {

    public AttacksOpponentWithMostLifeTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever this creature attacks a player, if no opponent has more life than that player, ");
    }

    private AttacksOpponentWithMostLifeTriggeredAbility(final AttacksOpponentWithMostLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat().getAttackers().contains(this.getSourceId())
                && game.getPlayer(game.getCombat().getDefenderId(this.getSourceId())) != null;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player defender = game.getPlayer(game.getCombat().getDefenderId(getSourceId()));
        return defender != null
                && game
                .getOpponents(getControllerId(), true)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .max()
                .orElse(Integer.MIN_VALUE)
                <= defender
                .getLife();
    }

    @Override
    public AttacksOpponentWithMostLifeTriggeredAbility copy() {
        return new AttacksOpponentWithMostLifeTriggeredAbility(this);
    }
}
