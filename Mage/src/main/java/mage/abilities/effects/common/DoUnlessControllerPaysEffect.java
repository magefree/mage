
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author MarcoMarin
 */
public class DoUnlessControllerPaysEffect extends OneShotEffect {

    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoUnlessControllerPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessControllerPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessControllerPaysEffect(final DoUnlessControllerPaysEffect effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    public void addEffect(Effect effect) {
        executingEffects.add(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + cost.getText() + " to prevent (" + effectText.substring(0, effectText.length() - 1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, sourceObject.getName());
            boolean result = true;
            boolean doEffect = true;
            
            // check if controller is willing to pay
            if (cost.canPay(source, source, controller.getId(), game) && controller.chooseUse(Outcome.Detriment, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, controller.getId(), false, null)) {
                    if (!game.isSimulation()) {
                        game.informPlayers(controller.getLogName() + " pays the cost to prevent the effect");
                    }
                    doEffect = false;                    
                }
            }

            // do the effects if not paid
            if (doEffect) {
                for (Effect effect : executingEffects) {
                    effect.setTargetPointer(this.targetPointer);
                    if (effect instanceof OneShotEffect) {
                        result &= effect.apply(game, source);
                    } else {
                        game.addEffect((ContinuousEffect) effect, source);
                    }
                }
            }
            return result;
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
        String effectsText = executingEffects.getText(mode);
        return effectsText.substring(0, effectsText.length() - 1) + " unless controller pays " + cost.getText();
    }

    @Override
    public DoUnlessControllerPaysEffect copy() {
        return new DoUnlessControllerPaysEffect(this);
    }
}
