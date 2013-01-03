package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

public class DoIfCostPaid extends OneShotEffect<DoIfCostPaid> {
    private OneShotEffect executingEffect;
    private Cost cost;

    public DoIfCostPaid(OneShotEffect effect, Cost cost) {
        super(Constants.Outcome.Benefit);
        this.executingEffect = effect;
        this.cost = cost;
    }

    public DoIfCostPaid(final DoIfCostPaid effect) {
        super(effect);
        this.executingEffect = (OneShotEffect) effect.executingEffect.copy();
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(executingEffect.getOutcome(), "Pay " + cost.getText() + " and " + executingEffect.getText(source.getModes().getMode()), game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getId(), source.getControllerId(), false)) {
                    executingEffect.setTargetPointer(this.targetPointer);
                    return executingEffect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("you may ");
        String costText = cost.getText();
        if (costText.length() <7 || !costText.substring(0, 7).toLowerCase().equals("discard")) {
            sb.append("pay ");
        }
        return sb.append(costText).append(". If you do, ").append(executingEffect.getText(mode)).toString();
    }

    @Override
    public DoIfCostPaid copy() {
        return new DoIfCostPaid(this);
    }
}

