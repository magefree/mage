
package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AsTurnedFaceUpEffect extends ReplacementEffectImpl {

    protected Effects baseEffects = new Effects();
    protected boolean optional;

    public AsTurnedFaceUpEffect(Effect baseEffect, boolean optional) {
        super(Duration.WhileOnBattlefield, baseEffect.getOutcome(), true);
        this.baseEffects.add(baseEffect);
        this.optional = optional;
    }

    public AsTurnedFaceUpEffect(final AsTurnedFaceUpEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.optional = effect.optional;
    }

    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNFACEUP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (optional) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject object = game.getObject(source);
            if (controller == null || object == null) {
                return false;
            }
            if (!controller.chooseUse(outcome, "Use effect of " + object.getIdName() + "?", source, game)) {
                return false;
            }
        }
        for (Effect effect : baseEffects) {
            if (source.activate(game, false)) {
                if (effect instanceof ContinuousEffect) {
                    game.addEffect((ContinuousEffect) effect, source);
                } else {
                    effect.apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "As {this} is turned face up, " + baseEffects.getText(mode);
    }

    @Override
    public AsTurnedFaceUpEffect copy() {
        return new AsTurnedFaceUpEffect(this);
    }

}
