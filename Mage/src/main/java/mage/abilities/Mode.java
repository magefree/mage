
package mage.abilities;

import java.io.Serializable;
import java.util.UUID;
import mage.abilities.effects.Effects;
import mage.target.Targets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mode implements Serializable {

    protected UUID id;
    protected Targets targets;
    protected Effects effects;

    public Mode() {
        this.id = UUID.randomUUID();
        this.targets = new Targets();
        this.effects = new Effects();
    }

    public Mode(Mode mode) {
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

    public Effects getEffects() {
        return effects;
    }
}
