package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author TheElk801
 */
public class FirstSpellOpponentsTurnTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterSpell defaultFilter
            = new FilterSpell("your first spell during each opponent's turn");

    public FirstSpellOpponentsTurnTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, SetTargetPointer.NONE);
    }

    public FirstSpellOpponentsTurnTriggeredAbility(Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(effect, defaultFilter, optional, setTargetPointer);
    }

    protected FirstSpellOpponentsTurnTriggeredAbility(final FirstSpellOpponentsTurnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FirstSpellOpponentsTurnTriggeredAbility copy() {
        return new FirstSpellOpponentsTurnTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.getControllerId()) // ignore controller turn
                || !game.getOpponents(this.getControllerId()).contains(game.getActivePlayerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null && (watcher.getCount(event.getPlayerId()) == 1) && super.checkTrigger(event, game)) {
            if (setTargetPointer == SetTargetPointer.PLAYER) { // not handled in super class
                getAllEffects().setTargetPointer(new FixedTarget(game.getActivePlayerId()));
            }
            return true;
        }
        return false;
    }
}
