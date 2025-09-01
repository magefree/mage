package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class GainDoubleLifeReplacementEffect extends ReplacementEffectImpl {

    public GainDoubleLifeReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would gain life, you gain twice that much life instead";
    }

    private GainDoubleLifeReplacementEffect(final GainDoubleLifeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GainDoubleLifeReplacementEffect copy() {
        return new GainDoubleLifeReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
