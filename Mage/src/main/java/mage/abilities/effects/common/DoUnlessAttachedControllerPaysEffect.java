package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class DoUnlessAttachedControllerPaysEffect extends OneShotEffect {

    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoUnlessAttachedControllerPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessAttachedControllerPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Neutral);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessAttachedControllerPaysEffect(final DoUnlessAttachedControllerPaysEffect effect) {
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
        Permanent aura = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (aura == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanentOrLKIBattlefield(aura.getAttachedTo());
        if (attachedTo == null) {
            return false;
        }
        Player controllerOfAttachedTo = game.getPlayer(attachedTo.getControllerId());
        if (controllerOfAttachedTo != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + cost.getText() + " to prevent (" + effectText.substring(0, effectText.length() - 1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, aura.getName());
            boolean result = true;
            boolean doEffect = true;

            // check if controller is willing to pay
            if (cost.canPay(source, source, controllerOfAttachedTo.getId(), game)
                    && controllerOfAttachedTo.chooseUse(Outcome.Neutral, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, controllerOfAttachedTo.getId(), false, null)) {
                    if (!game.isSimulation()) {
                        game.informPlayers(controllerOfAttachedTo.getLogName() + " pays the cost to prevent the effect");
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

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        String effectsText = executingEffects.getText(mode);
        return effectsText.substring(0, effectsText.length() - 1) + " unless controller pays " + cost.getText();
    }

    @Override
    public DoUnlessAttachedControllerPaysEffect copy() {
        return new DoUnlessAttachedControllerPaysEffect(this);
    }
}
