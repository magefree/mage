package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author spjspj
 */
public class RollDiceEffect extends OneShotEffect {

    protected Effects executingEffects = new Effects();
    protected int numSides;

    public RollDiceEffect(Effect effect, int numSides) {
        this(effect, Outcome.Neutral, numSides);
    }

    public RollDiceEffect(Effect effect, Outcome outcome, int numSides) {
        super(outcome);
        addEffect(effect);
        this.numSides = numSides;
    }

    public RollDiceEffect(final RollDiceEffect effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.numSides = effect.numSides;
    }

    public void addEffect(Effect effect) {
        if (effect != null) {
            executingEffects.add(effect);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (controller != null && mageObject != null) {
            controller.rollDice(outcome, source, game, numSides);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return "Roll a " + numSides + " sided die";
    }

    @Override
    public RollDiceEffect copy() {
        return new RollDiceEffect(this);
    }
}
