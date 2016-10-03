/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects;

import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.abilities.MageSingleton;
import mage.abilities.Mode;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.TargetPointer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        this.effectType = effect.effectType;
        this.staticText = effect.staticText;
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
    public void setText(String staticText) {
        this.staticText = staticText;
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
    public void setTargetPointer(TargetPointer targetPointer) {
        this.targetPointer = targetPointer;
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

    /**
     * If set, the game.applyEffects() method will be called to apply the effects before the
     * next effect (of the same ability) will resolve.
     */
    @Override
    public void setApplyEffectsAfter() {
        applyEffectsAfter = true;
    }

    @Override
    public boolean applyEffectsAfter() {
        return applyEffectsAfter;
    }
    
    @Override
    public void clearEffectTargets() {
    }
}
