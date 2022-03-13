

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
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class DoIfClashWonEffect extends OneShotEffect {
    protected final Effect executingEffect;
    private String chooseUseText;
    private boolean setTargetPointerToClashedOpponent;

    public DoIfClashWonEffect(Effect effect) {
        this(effect, false, null);
    }

    public DoIfClashWonEffect(Effect effect, boolean setTargetPointerToClashedOpponent, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.chooseUseText = chooseUseText;        
        this.setTargetPointerToClashedOpponent = setTargetPointerToClashedOpponent;
    }

    public DoIfClashWonEffect(final DoIfClashWonEffect effect) {
        super(effect);
        this.executingEffect = effect.executingEffect.copy();
        this.chooseUseText = effect.chooseUseText;
        this.setTargetPointerToClashedOpponent = effect.setTargetPointerToClashedOpponent;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getPayingPlayer(game, source);
        MageObject mageObject = game.getObject(source);
        if (player != null && mageObject != null) {
            String message = null;
            if (chooseUseText != null) {
                message = chooseUseText;
                message = CardUtil.replaceSourceName(message, mageObject.getLogName());
            }
            
            if (chooseUseText == null || player.chooseUse(executingEffect.getOutcome(), message, source, game)) {
                if (ClashEffect.getInstance().apply(game, source)) {
                    if (setTargetPointerToClashedOpponent) {
                        Object opponent = getValue("clashOpponent");
                        if (opponent instanceof Player) {
                            executingEffect.setTargetPointer(new FixedTarget(((Player)opponent).getId()));
                        }                        
                    } else {
                        executingEffect.setTargetPointer(this.targetPointer);
                    }
                    if (executingEffect instanceof OneShotEffect) {
                        return executingEffect.apply(game, source);
                    }
                    else {
                        game.addEffect((ContinuousEffect) executingEffect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }

    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return new StringBuilder("clash with an opponent. If you win, ").append(executingEffect.getText(mode)).toString();
    }

    @Override
    public DoIfClashWonEffect copy() {
        return new DoIfClashWonEffect(this);
    }
}
