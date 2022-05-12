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
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class DoUnlessAnyPlayerPaysEffect extends OneShotEffect {

    protected Effects executingEffects = new Effects();
    protected Cost cost;
    private String chooseUseText;
    protected DynamicValue genericMana;

    public DoUnlessAnyPlayerPaysEffect(Effect effect, DynamicValue genericMana) {
        super(Outcome.Detriment);
        this.genericMana = genericMana;
        this.executingEffects.add(effect);
    }

    public DoUnlessAnyPlayerPaysEffect(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoUnlessAnyPlayerPaysEffect(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Neutral);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoUnlessAnyPlayerPaysEffect(final DoUnlessAnyPlayerPaysEffect effect) {
        super(effect);
        if (effect.cost != null) {
            this.cost = effect.cost.copy();
        }
        if (effect.genericMana != null) {
            this.genericMana = effect.genericMana.copy();
        }
        this.executingEffects = effect.executingEffects.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    public void addEffect(Effect effect) {
        executingEffects.add(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        Cost costToPay;
        String costValueMessage;
        if (controller != null && sourceObject != null) {
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
            // check if any player is willing to pay
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.canRespond()
                        && costToPay.canPay(source, source, player.getId(), game)
                        && player.chooseUse(Outcome.Detriment, message, source, game)) {
                    costToPay.clearPaid();
                    if (costToPay.pay(source, game, source, player.getId(), false, null)) {
                        if (!game.isSimulation()) {
                            game.informPlayers(player.getLogName() + " pays the cost to prevent the effect");
                        }
                        doEffect = false;
                        break;
                    }
                }
            }
            // do the effects if nobody paid
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
        StringBuilder sb = new StringBuilder();
        if (cost != null) {
            sb.append(cost.getText());
        } else {
            sb.append("{X}");
        }
        if (genericMana != null && !genericMana.getMessage().isEmpty()) {
            sb.append(", where X is ");
            sb.append(genericMana.getMessage());
        }
        String effectsText = executingEffects.getText(mode);
        return effectsText.substring(0, effectsText.length() - 1) + " unless any player pays " + sb.toString();
    }

    @Override
    public DoUnlessAnyPlayerPaysEffect copy() {
        return new DoUnlessAnyPlayerPaysEffect(this);
    }
}
