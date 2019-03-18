
package mage.abilities.effects.common;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class PreventDamageByColorEffect extends PreventionEffectImpl {

    private final ObjectColor color;

    public PreventDamageByColorEffect(ObjectColor color, int amount) {
        super(Duration.WhileOnBattlefield, amount, false, false);
        this.color = color;
        this.staticText = "If a " + color.getDescription() + " source would deal damage to you, prevent " + amount + " of that damage";
    }

    public PreventDamageByColorEffect(PreventDamageByColorEffect effect) {
        super(effect);
        this.color = effect.color;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getControllerId())) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.getColor(game).contains(color)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public PreventDamageByColorEffect copy() {
        return new PreventDamageByColorEffect(this);
    }
}
