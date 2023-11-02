package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */

public class DoIfClashWonEffect extends OneShotEffect {
    protected final Effect executingEffect;
    private final boolean setTargetPointerToClashedOpponent;

    public DoIfClashWonEffect(Effect effect) {
        this(effect, false);
    }

    public DoIfClashWonEffect(Effect effect, boolean setTargetPointerToClashedOpponent) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.setTargetPointerToClashedOpponent = setTargetPointerToClashedOpponent;
    }

    protected DoIfClashWonEffect(final DoIfClashWonEffect effect) {
        super(effect);
        this.executingEffect = effect.executingEffect.copy();
        this.setTargetPointerToClashedOpponent = effect.setTargetPointerToClashedOpponent;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (player == null || mageObject == null) {
            return false;
        }
        if (new ClashEffect().apply(game, source)) {
            if (setTargetPointerToClashedOpponent) {
                Player opponent = game.getPlayer((UUID) getValue("clashOpponent"));
                if (opponent != null) {
                    executingEffect.setTargetPointer(new FixedTarget(opponent.getId()));
                }
            } else {
                executingEffect.setTargetPointer(this.targetPointer);
            }
            if (executingEffect instanceof OneShotEffect) {
                return executingEffect.apply(game, source);
            } else {
                game.addEffect((ContinuousEffect) executingEffect, source);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return "clash with an opponent. If you win, " + executingEffect.getText(mode);
    }

    @Override
    public DoIfClashWonEffect copy() {
        return new DoIfClashWonEffect(this);
    }
}
