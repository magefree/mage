
package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author LoneFox
 */
public class CastOnlyIfYouHaveCastAnotherSpellEffect extends ContinuousRuleModifyingEffectImpl {

    public CastOnlyIfYouHaveCastAnotherSpellEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast this spell only if you've cast another spell this turn";
    }

    protected CastOnlyIfYouHaveCastAnotherSpellEffect(final CastOnlyIfYouHaveCastAnotherSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CastOnlyIfYouHaveCastAnotherSpellEffect copy() {
        return new CastOnlyIfYouHaveCastAnotherSpellEffect(this);
    }
}
