
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class PreventAllDamageToPlayersEffect extends PreventionEffectImpl {

    public PreventAllDamageToPlayersEffect(Duration duration, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        staticText = setText();
    }

    public PreventAllDamageToPlayersEffect(final PreventAllDamageToPlayersEffect effect) {
        super(effect);
    }

    @Override
    public PreventAllDamageToPlayersEffect copy() {
        return new PreventAllDamageToPlayersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && game.getState().getPlayersInRange(controller.getId(), game).contains(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to players");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        return sb.toString();
    }
}
