package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class FlipCoinEffect extends OneShotEffect {

    protected Effects executingEffectsWon = new Effects();
    protected Effects executingEffectsLost = new Effects();

    public FlipCoinEffect() {
        this((Effect) null);
    }

    public FlipCoinEffect(Effect effectWon) {
        this(effectWon, null);
    }

    public FlipCoinEffect(Effect effectWon, Effect effectLost) {
        this(effectWon, effectLost, Outcome.Benefit);

    }

    public FlipCoinEffect(Effect effectWon, Effect effectLost, Outcome outcome) {
        super(outcome);
        addEffectWon(effectWon);
        addEffectLost(effectLost);
    }

    public FlipCoinEffect(final FlipCoinEffect effect) {
        super(effect);
        this.executingEffectsWon = effect.executingEffectsWon.copy();
        this.executingEffectsLost = effect.executingEffectsLost.copy();
    }

    public void addEffectWon(Effect effect) {
        if (effect != null) {
            executingEffectsWon.add(effect);
        }
    }

    public void addEffectLost(Effect effect) {
        if (effect != null) {
            executingEffectsLost.add(effect);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (controller == null || mageObject == null) {
            return false;
        }
        boolean result = true;
        for (Effect effect : controller.flipCoin(source, game, true) ? executingEffectsWon : executingEffectsLost) {
            effect.setTargetPointer(this.targetPointer);
            if (effect instanceof OneShotEffect) {
                result &= effect.apply(game, source);
            } else {
                game.addEffect((ContinuousEffect) effect, source);
            }
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("flip a coin");
        if (!executingEffectsWon.isEmpty()) {
            sb.append(". If you win the flip, ").append(executingEffectsWon.getText(mode));
        }
        if (!executingEffectsLost.isEmpty()) {
            sb.append(" If you lose the flip, ").append(executingEffectsLost.getText(mode));
        }
        return sb.toString();
    }

    @Override
    public FlipCoinEffect copy() {
        return new FlipCoinEffect(this);
    }
}
