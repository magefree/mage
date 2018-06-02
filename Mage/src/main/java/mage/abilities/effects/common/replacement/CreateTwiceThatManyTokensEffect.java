
package mage.abilities.effects.common.replacement;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class CreateTwiceThatManyTokensEffect extends ReplacementEffectImpl {

    public CreateTwiceThatManyTokensEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy);
        staticText = "If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead";
    }

    public CreateTwiceThatManyTokensEffect(final CreateTwiceThatManyTokensEffect effect) {
        super(effect);
    }

    @Override
    public CreateTwiceThatManyTokensEffect copy() {
        return new CreateTwiceThatManyTokensEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

}
