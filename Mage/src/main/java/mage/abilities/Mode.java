package mage.abilities;

import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Mode implements Serializable {

    protected UUID id;
    protected final Targets targets;
    protected final Effects effects;
    protected String flavorWord;

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
        this.flavorWord = mode.flavorWord;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Mode that = (Mode) obj;

        if (!Objects.equals(this.id, that.id)) {
            return false;
        }

        if (!Objects.equals(this.flavorWord, that.flavorWord)) {
            return false;
        }

        if (!Objects.equals(this.targets, that.targets)) {
            return false;
        }

        return Objects.equals(this.effects, that.effects);
    }

    public boolean equivalent(Object obj, Game game) {
        if (this == obj) {
            return true;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Mode that = (Mode) obj;

        if (!Objects.equals(this.flavorWord, that.flavorWord)) {
            return false;
        }

        if (!this.targets.equivalent(that.targets, game)) {
            return false;
        }

        return this.effects != null && this.effects.equivalent(that.effects, game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, targets, effects, flavorWord);
    }
}
