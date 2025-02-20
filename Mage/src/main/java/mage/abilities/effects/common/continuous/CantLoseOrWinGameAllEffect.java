package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author padfoot
 */
public class CantLoseOrWinGameAllEffect extends ContinuousRuleModifyingEffectImpl {

    public CantLoseOrWinGameAllEffect() {
	this(Duration.WhileOnBattlefield);
    }

    public CantLoseOrWinGameAllEffect(Duration duration) {
        super(duration, Outcome.Benefit, false, false);
	staticText = "players can't lose the game or win the game " 
		+ (duration == Duration.EndOfTurn ? "this turn":duration.toString());
    }
    
    private CantLoseOrWinGameAllEffect(final CantLoseOrWinGameAllEffect effect) {
        super(effect);
    }

    @Override
    public CantLoseOrWinGameAllEffect copy() {
        return new CantLoseOrWinGameAllEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.LOSES || event.getType() == GameEvent.EventType.WINS); 
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
	return true;
    }
}
