
package mage.abilities.effects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.MageSingleton;
import mage.abilities.Mode;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class EffectImpl implements Effect {

    protected UUID id;
    protected Outcome outcome;
    protected EffectType effectType;
    protected TargetPointer targetPointer = FirstTargetPointer.getInstance();
    protected String staticText = "";
    protected Map<String, Object> values;
    protected boolean applyEffectsAfter = false;

    public EffectImpl(Outcome outcome) {
        this.id = UUID.randomUUID();
        this.outcome = outcome;
    }

    public EffectImpl(final EffectImpl effect) {
        this.id = effect.id;
        this.outcome = effect.outcome;
        this.staticText = effect.staticText;
        this.effectType = effect.effectType;
        this.targetPointer = effect.targetPointer.copy();
        if (effect.values != null) {
            values = new HashMap<>();
            Map<String, Object> map = effect.values;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                values.put(entry.getKey(), entry.getValue());
            }
        }
        this.applyEffectsAfter = effect.applyEffectsAfter;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public Effect setText(String staticText) {
        this.staticText = staticText;
        return this;
    }

    @Override
    public Outcome getOutcome() {
        return outcome;
    }

    @Override
    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public Effect setTargetPointer(TargetPointer targetPointer) {
        this.targetPointer = targetPointer;
        return this;
    }

    @Override
    public TargetPointer getTargetPointer() {
        return this.targetPointer;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.id = UUID.randomUUID();
        }
    }

    @Override
    public void setValue(String key, Object value) {
        synchronized (this) {
            if (values == null) {
                values = new HashMap<>();
            }
        }
        values.put(key, value);
    }

    @Override
    public Object getValue(String key) {
        if (values == null) { // no value was set
            return null;
        }
        return values.get(key);
    }
}
