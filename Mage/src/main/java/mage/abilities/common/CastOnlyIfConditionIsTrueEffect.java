
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class CastOnlyIfConditionIsTrueEffect extends ContinuousRuleModifyingEffectImpl {

    private final Condition condition;

    public CastOnlyIfConditionIsTrueEffect(Condition condition) {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.condition = condition;
        staticText = setText();
    }

    private CastOnlyIfConditionIsTrueEffect(final CastOnlyIfConditionIsTrueEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // has to return true, if the spell cannot be cast in the current phase / step
        if (event.getSourceId().equals(source.getSourceId())) {
            if (condition != null && !condition.apply(game, source)) {
                return true;
            }
        }
        return false; // cast not prevented by this effect
    }

    @Override
    public CastOnlyIfConditionIsTrueEffect copy() {
        return new CastOnlyIfConditionIsTrueEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("cast this spell only if ");
        if (condition != null) {
            sb.append(condition);
        }
        return sb.toString();
    }
}
