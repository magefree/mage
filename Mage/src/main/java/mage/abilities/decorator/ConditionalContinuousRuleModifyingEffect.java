
package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.Condition;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class ConditionalContinuousRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    protected ContinuousRuleModifyingEffect effect;
    protected ContinuousRuleModifyingEffect otherwiseEffect;
    protected Condition condition;
    protected Condition baseCondition;
    protected boolean initDone = false;

    public ConditionalContinuousRuleModifyingEffect(ContinuousRuleModifyingEffect effect, Condition condition) {
        this(effect, condition, null);
    }

    public ConditionalContinuousRuleModifyingEffect(ContinuousRuleModifyingEffect effect, Condition condition, ContinuousRuleModifyingEffect otherwiseEffect) {
        super(effect.getDuration(), effect.getOutcome());
        this.effect = effect;
        this.baseCondition = condition;
        this.otherwiseEffect = otherwiseEffect;
    }

    public ConditionalContinuousRuleModifyingEffect(final ConditionalContinuousRuleModifyingEffect effect) {
        super(effect);
        this.effect = (ContinuousRuleModifyingEffect) effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = (ContinuousRuleModifyingEffect) effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
        this.baseCondition = effect.baseCondition;
        this.initDone = effect.initDone;
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
    public boolean isDiscarded() {
        return effect.isDiscarded() || (otherwiseEffect != null && otherwiseEffect.isDiscarded());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        if (effect.checksEventType(event, game)) {
            return true;
        } else if (otherwiseEffect != null) {
            return otherwiseEffect.checksEventType(event, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!initDone) { // if simpleStaticAbility, init won't be called
            init(source, game);
        }
        if (condition.apply(game, source)) {
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
        if (staticText == null || staticText.isEmpty() && this.effect != null) { // usefull for conditional night/day card abilities
            return effect.getText(mode);
        }
        return staticText;
    }

    @Override
    public ConditionalContinuousRuleModifyingEffect copy() {
        return new ConditionalContinuousRuleModifyingEffect(this);
    }

    @Override
    public boolean sendMessageToGameLog() {
        return effect.sendMessageToGameLog(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendMessageToUser() {
        return effect.sendMessageToUser(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return effect.getInfoMessage(source, event, game); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
