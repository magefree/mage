package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class CantCastDuringFirstThreeTurnsEffect extends ContinuousRuleModifyingEffectImpl {

    public CantCastDuringFirstThreeTurnsEffect() {
        this("this spell");
    }

    public CantCastDuringFirstThreeTurnsEffect(String selfName) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "you can't cast " + selfName + " during your first, second, or third turns of the game";
    }

    private CantCastDuringFirstThreeTurnsEffect(final CantCastDuringFirstThreeTurnsEffect effect) {
        super(effect);
    }

    @Override
    public CantCastDuringFirstThreeTurnsEffect copy() {
        return new CantCastDuringFirstThreeTurnsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getSourceId().equals(source.getSourceId())) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.getTurns() <= 3 && game.isActivePlayer(source.getControllerId());
    }
}
