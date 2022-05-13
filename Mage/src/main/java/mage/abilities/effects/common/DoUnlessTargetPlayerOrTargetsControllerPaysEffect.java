package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.ManaUtil;

/**
 * @author MarcoMarin & L_J
 */
public class DoUnlessTargetPlayerOrTargetsControllerPaysEffect extends OneShotEffect {

    protected Effects executingEffects = new Effects();
    private Effect otherwiseEffect;
    private Cost cost;
    private DynamicValue genericMana;
    private String chooseUseText;

    public DoUnlessTargetPlayerOrTargetsControllerPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessTargetPlayerOrTargetsControllerPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        this(effect, null, cost, chooseUseText);
    }

    public DoUnlessTargetPlayerOrTargetsControllerPaysEffect(Effect effect, Effect otherwiseEffect, Cost cost, String chooseUseText) {
        super(Outcome.Detriment);
        this.executingEffects.add(effect);
        this.otherwiseEffect = otherwiseEffect;
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessTargetPlayerOrTargetsControllerPaysEffect(Effect effect, DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.executingEffects.add(effect);
        this.genericMana = genericMana;
    }

    public DoUnlessTargetPlayerOrTargetsControllerPaysEffect(final DoUnlessTargetPlayerOrTargetsControllerPaysEffect effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.otherwiseEffect = effect.otherwiseEffect;
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
        this.chooseUseText = effect.chooseUseText;
    }

    public void addEffect(Effect effect) {
        executingEffects.add(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent targetPermanent = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if (targetPermanent != null) {
            player = game.getPlayer(targetPermanent.getControllerId());
        }
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            Cost costToPay;
            String costValueMessage;
            if (cost != null) {
                costToPay = cost.copy();
                costValueMessage = costToPay.getText();
            } else {
                costToPay = ManaUtil.createManaCost(genericMana, game, source, this);
                costValueMessage = "{" + genericMana.calculate(game, source, this) + "}";
            }
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                message = "Pay " + costValueMessage + " to prevent (" + effectText.substring(0, effectText.length() - 1) + ")?";
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, sourceObject.getName());
            boolean result = true;
            boolean doEffect = true;

            // check if targetController is willing to pay
            if (costToPay.canPay(source, source, player.getId(), game)
                    && player.chooseUse(Outcome.Detriment, message, source, game)) {
                costToPay.clearPaid();
                if (costToPay.pay(source, game, source, player.getId(), false, null)) {
                    if (!game.isSimulation()) {
                        game.informPlayers(player.getLogName() + " pays the cost to prevent the effect");
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
            } else if (otherwiseEffect != null) {
                otherwiseEffect.setTargetPointer(this.targetPointer);
                if (otherwiseEffect instanceof OneShotEffect) {
                    result &= otherwiseEffect.apply(game, source);
                } else {
                    game.addEffect((ContinuousEffect) otherwiseEffect, source);
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
        return effectsText.substring(0, effectsText.length() - 1) + " unless its controller pays " + (cost != null ? cost.getText() : "{X}");
    }

    @Override
    public DoUnlessTargetPlayerOrTargetsControllerPaysEffect copy() {
        return new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(this);
    }
}
