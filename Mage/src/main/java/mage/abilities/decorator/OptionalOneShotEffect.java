package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

/**
 * Adds condition to {@link OneShotEffect}. Acts as decorator.
 *
 * @author Grath
 */
public class OptionalOneShotEffect extends OneShotEffect {

    private final Effects effects = new Effects();

    public OptionalOneShotEffect(OneShotEffect effect) {
        super(effect != null ? effect.getOutcome() : Outcome.Benefit); // must be first line, can't error for null effect here.
        if (effect == null) {
            throw new IllegalArgumentException("Wrong code usage: OptionalOneShotEffect should start with an effect to generate Outcome.");
        }
        this.effects.add(effect);
    }

    protected OptionalOneShotEffect(final OptionalOneShotEffect effect) {
        super(effect);
        this.effects.addAll(effect.effects.copy());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // nothing to do - no problem
        if (effects.isEmpty()) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        String chooseText = staticText;
        if (chooseText == null || chooseText.isEmpty()) {
            chooseText = getText(source.getModes().getMode());
            chooseText = Character.toUpperCase(chooseText.charAt(0)) + chooseText.substring(1);
        }
        if (player != null && player.chooseUse(outcome, chooseText, source, game)) {
            effects.setTargetPointer(this.getTargetPointer().copy());
            effects.forEach(effect -> effect.apply(game, source));
            return true;
        }
        return false;
    }

    public OptionalOneShotEffect addEffect(OneShotEffect effect) {
        this.effects.add(effect);
        return this;
    }

    @Override
    public void setValue(String key, Object value) {
        super.setValue(key, value);
        this.effects.setValue(key, value);
    }

    @Override
    public OptionalOneShotEffect copy() {
        return new OptionalOneShotEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "you may " + CardUtil.getTextWithFirstCharLowerCase(effects.getText(mode));
    }

    @Override
    public OptionalOneShotEffect setTargetPointer(TargetPointer targetPointer) {
        effects.setTargetPointer(targetPointer);
        super.setTargetPointer(targetPointer);
        return this;
    }

    @Override
    public OptionalOneShotEffect withTargetDescription(String target) {
        effects.forEach(effect -> effect.withTargetDescription(target));
        return this;
    }
}
