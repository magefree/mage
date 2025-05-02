package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Based on {@link EntersBattlefieldEffect}.
 * This allows rule wording such as "as {this} becomes attached to a creature..."
 * For this, there should not be a trigger, as in the case of the wording "when..."
 * As per rule 614.1c, this should be a replacement effect.
 * See [[Sanctuary Blade]].
 *
 * @author DominionSpy
 */
public class BecomesAttachedToCreatureSourceEffect extends ReplacementEffectImpl {

    private final Effects baseEffects;
    private final String text;
    private final Condition condition;

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect) {
        this(baseEffect, "");
    }

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect, String text) {
        this(baseEffect, null, text);
    }

    public BecomesAttachedToCreatureSourceEffect(Effect baseEffect, Condition condition, String text) {
        super(Duration.WhileOnBattlefield, baseEffect.getOutcome(), false);
        this.baseEffects = new Effects();
        this.baseEffects.add(baseEffect);
        this.text = text;
        this.condition = condition;
    }

    protected BecomesAttachedToCreatureSourceEffect(final BecomesAttachedToCreatureSourceEffect effect) {
        super(effect);
        this.baseEffects = effect.baseEffects.copy();
        this.text = effect.text;
        this.condition = effect.condition;
    }

    @Override
    public BecomesAttachedToCreatureSourceEffect copy() {
        return new BecomesAttachedToCreatureSourceEffect(this);
    }

    public void addEffect(Effect effect) {
        baseEffects.add(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.ATTACH == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            return condition == null || condition.apply(game, source);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        for (Effect effect : baseEffects) {
            if (effect instanceof ContinuousEffect) {
                game.addEffect((ContinuousEffect) effect, source);
            } else {
                effect.setValue("appliedEffects", event.getAppliedEffects());
                effect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return (text == null || text.isEmpty()) ? baseEffects.getText(mode) : text;
    }
}
