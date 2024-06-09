package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author xenohedron
 */
public class CantCastDuringYourTurnEffect extends ContinuousRuleModifyingEffectImpl {

    /**
     * "Your opponents can't cast spells during your turn."
     */
    public CantCastDuringYourTurnEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells during your turn";
    }

    protected CantCastDuringYourTurnEffect(final CantCastDuringYourTurnEffect effect) {
        super(effect);
    }

    @Override
    public CantCastDuringYourTurnEffect copy() {
        return new CantCastDuringYourTurnEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.isActivePlayer(source.getControllerId()) &&
                game.getPlayer(source.getControllerId()).hasOpponent(event.getPlayerId(), game);
    }
}
