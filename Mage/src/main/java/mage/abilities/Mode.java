package mage.abilities;

import java.io.Serializable;
import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.target.Target;
import mage.target.Targets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mode implements Serializable {

    protected UUID id;
    protected final Targets targets;
    protected final Effects effects;

    public Mode() {
        this((Effect) null);
    }

    public Mode(Effect effect) {
        this.id = UUID.randomUUID();
        this.targets = new Targets();
        this.effects = new Effects();
        if (effect != null) {
            this.effects.add(effect);
        }
    }

    public Mode(final Mode mode) {
        this.id = mode.id;
        this.targets = mode.targets.copy();
        this.effects = mode.effects.copy();
    }

    public UUID setRandomId() {
        return this.id = UUID.randomUUID();
    }

    public Mode copy() {
        return new Mode(this);
    }

    public UUID getId() {
        return id;
    }

    public Targets getTargets() {
        return targets;
    }

    public void addTarget(Target target) {
        targets.add(target);
    }

    public Effects getEffects() {
        return effects;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }
}
