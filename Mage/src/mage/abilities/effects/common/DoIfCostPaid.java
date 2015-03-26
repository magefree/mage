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

public class DoIfCostPaid extends OneShotEffect {
    protected Effects executingEffects = new Effects();
    private final Cost cost;
    private String chooseUseText;

    public DoIfCostPaid(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoIfCostPaid(Effect effect, Cost cost, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffects.add(effect);
        this.cost = cost;
        this.chooseUseText = chooseUseText;
    }

    public DoIfCostPaid(final DoIfCostPaid effect) {
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
        Player player = getPayingPlayer(game, source);
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            String message;
            if (chooseUseText == null) {
                message = new StringBuilder(getCostText()).append(" and ").append(executingEffects.getText(source.getModes().getMode())).append("?").toString();
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, mageObject.getLogName());
            boolean result = true;
            if (cost.canPay(source, source.getSourceId(), player.getId(), game) && player.chooseUse(executingEffects.get(0).getOutcome(), message, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    
                    for(Effect effect: executingEffects) {
                        effect.setTargetPointer(this.targetPointer);
                        if (effect instanceof OneShotEffect) {
                            result &= effect.apply(game, source);
                        }
                        else {
                            game.addEffect((ContinuousEffect) effect, source);
                        }
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
        return new StringBuilder("you may ").append(getCostText()).append(". If you do, ").append(executingEffects.getText(mode)).toString();
    }

    protected String getCostText() {
        StringBuilder sb = new StringBuilder();
        String costText = cost.getText();
        if (costText != null
                && !costText.toLowerCase().startsWith("discard")
                && !costText.toLowerCase().startsWith("sacrifice")
                && !costText.toLowerCase().startsWith("remove")
                && !costText.toLowerCase().startsWith("pay")) {
            sb.append("pay ");
        }
        return sb.append(costText).toString();
    }

    @Override
    public DoIfCostPaid copy() {
        return new DoIfCostPaid(this);
    }
}

