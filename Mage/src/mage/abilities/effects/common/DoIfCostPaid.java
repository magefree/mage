package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

public class DoIfCostPaid extends OneShotEffect<DoIfCostPaid> {
    private final OneShotEffect executingEffect;
    private final Cost cost;
    private String chooseUseText;

    public DoIfCostPaid(OneShotEffect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoIfCostPaid(OneShotEffect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoIfCostPaid(final DoIfCostPaid effect) {
        super(effect);
        this.executingEffect = (OneShotEffect) effect.executingEffect.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            String message;
            if (chooseUseText == null) {
                message = new StringBuilder(getCostText()).append(" and ").append(executingEffect.getText(source.getModes().getMode())).append("?").toString();
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, mageObject.getName());
            if (player.chooseUse(executingEffect.getOutcome(), message, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
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
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return new StringBuilder("you may ").append(getCostText()).append(". If you do, ").append(executingEffect.getText(mode)).toString();
    }

    private String getCostText() {
        StringBuilder sb = new StringBuilder();
        String costText = cost.getText();
        if (costText != null &&
                !costText.toLowerCase().startsWith("discard")
                && !costText.toLowerCase().startsWith("sacrifice")
                && !costText.toLowerCase().startsWith("remove")) {
            sb.append("pay ");
        }
        return sb.append(costText).toString();
    }

    @Override
    public DoIfCostPaid copy() {
        return new DoIfCostPaid(this);
    }
}

