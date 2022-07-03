package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.effects.PreventionEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author JayDi85
 */
public class ConditionalPreventionEffect extends PreventionEffectImpl {

    protected PreventionEffect effect;
    protected PreventionEffect otherwiseEffect;
    protected Condition baseCondition;
    protected Condition condition;
    protected boolean conditionState;
    protected boolean initDone = false;

    public ConditionalPreventionEffect(PreventionEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    /**
     * Only use this if both effects have the same layers
     *
     * @param effect
     * @param otherwiseEffect
     * @param condition
     * @param text
     */
    public ConditionalPreventionEffect(PreventionEffect effect, PreventionEffect otherwiseEffect, Condition condition, String text) {
        super(effect.getDuration());
        this.effect = effect;
        this.otherwiseEffect = otherwiseEffect;
        this.baseCondition = condition;
        this.staticText = text;
    }

    public ConditionalPreventionEffect(final ConditionalPreventionEffect effect) {
        super(effect);
        this.effect = (PreventionEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (PreventionEffect) effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;  // TODO: checks conditional copy -- it's can be usefull for memory leaks fix?
        this.conditionState = effect.conditionState;
        this.baseCondition = effect.baseCondition;
        this.initDone = effect.initDone;
    }

    @Override
    public boolean isDiscarded() {
        return this.discarded || effect.isDiscarded() || (otherwiseEffect != null && otherwiseEffect.isDiscarded());
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (baseCondition instanceof LockedInCondition) {
            condition = new FixedCondition(((LockedInCondition) baseCondition).getBaseCondition().apply(game, source));
        } else {
            condition = baseCondition;
        }
        effect.setTargetPointer(this.targetPointer);
        effect.init(source, game);
        if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            otherwiseEffect.init(source, game);
        }
        initDone = true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.replaceEvent(event, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.replaceEvent(event, source, game);
        }

        if (!conditionState && effect.getDuration() == Duration.OneUse) {
            used = true;
        }
        if (!conditionState && effect.getDuration() == Duration.Custom) {
            this.discard();
        }

        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return effect.checksEventType(event, game)
                || (otherwiseEffect != null && otherwiseEffect.checksEventType(event, game));
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!initDone) { // if simpleStaticAbility, init won't be called
            init(source, game);
        }
        conditionState = condition.apply(game, source);
        if (conditionState) {
            effect.setTargetPointer(this.targetPointer);
            return effect.applies(event, source, game);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
            return otherwiseEffect.applies(event, source, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if ((staticText == null || staticText.isEmpty()) && this.effect != null) { // usefull for conditional night/day card abilities
            return effect.getText(mode);
        }
        return staticText;
    }

    @Override
    public ConditionalPreventionEffect copy() {
        return new ConditionalPreventionEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
