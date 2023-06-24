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
public class GainPlusOneLifeReplacementEffect extends ReplacementEffectImpl {

    public GainPlusOneLifeReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would gain life, you gain that much life plus 1 instead";
    }

    private GainPlusOneLifeReplacementEffect(final GainPlusOneLifeReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GainPlusOneLifeReplacementEffect copy() {
        return new GainPlusOneLifeReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
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
