package mage.abilities.effects;

import mage.abilities.MageSingleton;
import mage.abilities.Mode;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.TargetPointer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class EffectImpl implements Effect {

    protected UUID id;
    protected Outcome outcome;
    protected EffectType effectType;

    // read related docs about static and dynamic targets in ContinuousEffectImpl.affectedObjectsSet
    protected TargetPointer targetPointer = FirstTargetPointer.getInstance();

    protected String staticText = "";
    protected Map<String, Object> values;
    protected String concatPrefix = ""; // combines multiple effects in text rule

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
        this.concatPrefix = effect.concatPrefix;
        if (effect.values != null) {
            values = new HashMap<>();
            Map<String, Object> map = effect.values;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                values.put(entry.getKey(), entry.getValue());
            }
        }
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

    @Override
    public Effect concatBy(String concatPrefix) {
        this.concatPrefix = concatPrefix;
        return this;
    }

    @Override
    public String getConcatPrefix() {
        return this.concatPrefix;
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.innerEquals(obj)) {
            return false;
        }
        EffectImpl that = (EffectImpl) obj;

        if (this.targetPointer == null || !this.targetPointer.equals(that.targetPointer)) {
            return false;
        }
        if (!(this.values != null && that.values != null)
                && this.values == null) {
            return false;
        }
        if (this.values.size() != that.values.size()) {
            return false;
        }
        for (String key : this.values.keySet()) {
            if (!that.values.containsKey(key)) {
                return false;
            }
            Object thisObject = this.values.get(key);
            Object thatObject = that.values.get(key);

            if (!Objects.equals(thisObject, thatObject)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equivalent(Object obj, Game game) {
        if (!this.innerEquals(obj)) {
            return false;
        }
        EffectImpl that = (EffectImpl) obj;

        if (this.targetPointer == null
                || !this.targetPointer.equivalent(that.targetPointer, game)) {
            return false;
        }

        if (!(this.values != null && that.values != null)
                && this.values == null) {
            return false;
        }
        if (this.values.size() != that.values.size()) {
            return false;
        }
        for (String key : this.values.keySet()) {
            if (!that.values.containsKey(key)) {
                return false;
            }
            Object thisObject = this.values.get(key);
            Object thatObject = that.values.get(key);

            Method equivalent = null;
            try {
                equivalent = thisObject.getClass().getMethod("equivalent", (Class<?>[]) new Class[]{Object.class, Game.class});
            } catch (NoSuchMethodException | SecurityException e) {
                // If we're here then the object does not have an equivalent method.
                // Check with equals instead.
                if (!Objects.equals(thisObject, thatObject)) {
                    return false;
                } else {
                    continue;
                }
            }
            // If we get here, then the object has an `equivalent` method
            try {
                if (!((Boolean)equivalent.invoke(thisObject, thatObject, game))) {
                    return false;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private boolean innerEquals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        EffectImpl that = (EffectImpl) obj;

        return Objects.equals(this.id, that.id)
                && this.outcome == that.outcome
                && this.effectType == that.effectType
                && Objects.equals(this.staticText, that.staticText)
                && Objects.equals(this.concatPrefix, that.concatPrefix);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, outcome, effectType, targetPointer, staticText, values, concatPrefix);
    }
}
