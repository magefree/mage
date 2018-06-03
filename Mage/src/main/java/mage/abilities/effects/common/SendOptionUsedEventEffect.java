
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class SendOptionUsedEventEffect extends OneShotEffect {

    private final int value;

    public SendOptionUsedEventEffect() {
        this(0);
    }

    public SendOptionUsedEventEffect(int value) {
        super(Outcome.Detriment);
        this.value = value;
    }

    public SendOptionUsedEventEffect(final SendOptionUsedEventEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public SendOptionUsedEventEffect copy() {
        return new SendOptionUsedEventEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.OPTION_USED, source.getOriginalId(), source.getSourceId(), source.getControllerId(), value));
        return true;
    }
}
