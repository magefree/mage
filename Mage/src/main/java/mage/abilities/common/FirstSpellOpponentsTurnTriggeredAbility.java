package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author TheElk801
 */
public class FirstSpellOpponentsTurnTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterSpell defaultFilter
            = new FilterSpell("your first spell during each opponent's turn");

    public FirstSpellOpponentsTurnTriggeredAbility(Effect effect, boolean optional) {
        super(effect, defaultFilter, optional);
    }

    private FirstSpellOpponentsTurnTriggeredAbility(final FirstSpellOpponentsTurnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FirstSpellOpponentsTurnTriggeredAbility copy() {
        return new FirstSpellOpponentsTurnTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.getControllerId()) // ignore controller turn
                || !super.checkTrigger(event, game)
                || !game.getOpponents(this.getControllerId()).contains(game.getActivePlayerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null && watcher.getCount(event.getPlayerId()) == 1;
    }
}
