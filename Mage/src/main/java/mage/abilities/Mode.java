package mage.abilities;

import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.target.Target;
import mage.target.Targets;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Mode implements Serializable {

    protected UUID id;
    protected final Targets targets;
    protected final Effects effects;
    protected String flavorWord;
    /**
     * Optional Tag to distinguish this mode from others.
     * In the case of modes that players can only choose once,
     * the tag is directly what is displayed in ModesAlreadyUsedHint
     */
    protected String modeTag;

    public Mode(Effect effect) {
        this.id = UUID.randomUUID();
        this.targets = new Targets();
        this.effects = new Effects();
        if (effect != null) {
            this.effects.add(effect);
        }
    }

    protected Mode(final Mode mode) {
        this.id = mode.id;
        this.targets = mode.targets.copy();
        this.effects = mode.effects.copy();
        this.flavorWord = mode.flavorWord;
        this.modeTag = mode.modeTag;
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

    public Mode addTarget(Target target) {
        return this.addTarget(target, false);
    }

    public Mode addTarget(Target target, Boolean addChooseHintFromEffect) {
        targets.add(target);
        if (addChooseHintFromEffect) {
            target.withChooseHint(this.getEffects().getText(this));
        }
        return this;
    }

    public Effects getEffects() {
        return effects;
    }

    public Mode addEffect(Effect effect) {
        effects.add(effect);
        return this;
    }

    /**
     * Tag the mode to be retrieved elsewhere thanks to the tag.
     */
    public Mode setModeTag(String tag) {
        this.modeTag = tag;
        return this;
    }

    /**
     * @return the mode tag for this mode, if set
     */
    public String getModeTag() {
        return this.modeTag == null ? "" : this.modeTag;
    }

    public String getFlavorWord() {
        return flavorWord;
    }

    /**
     * Set Flavor word for choice in the mode (same as ability/ancher words, but uses for lore/info and represents a possible choices)
     *
     * @param flavorWord
     * @return
     */
    public Mode withFlavorWord(String flavorWord) {
        this.flavorWord = flavorWord;
        return this;
    }
}
