package mage.abilities.effects.common;

import java.util.Locale;
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
    protected Effects otherwiseEffects = new Effects(); // used for Imprison
    private final Cost cost;
    private String chooseUseText;
    private boolean optional;

    public DoIfCostPaid(Effect effect, Cost cost) {
        this(effect, cost, null);
    }

    public DoIfCostPaid(Effect effect, Effect effect2, Cost cost) {
        this(effect,effect2,cost,true);
    }
    public DoIfCostPaid(Effect effect, Effect effect2, Cost cost,boolean optional) {
        this(effect, cost, null, optional);
        this.otherwiseEffects.add(effect2);
    }

    public DoIfCostPaid(Effect effect, Cost cost, String chooseUseText) {
        this(effect, cost, chooseUseText, true);
    }

    public DoIfCostPaid(Effect effect, Cost cost, String chooseUseText, boolean optional) {
        super(Outcome.Benefit);
        if (effect != null) {
            this.executingEffects.add(effect);
        }
        this.cost = cost;
        this.chooseUseText = chooseUseText;
        this.optional = optional;
    }

    public DoIfCostPaid(final DoIfCostPaid effect) {
        super(effect);
        this.executingEffects = effect.executingEffects.copy();
        this.otherwiseEffects = effect.otherwiseEffects.copy();
        this.cost = effect.cost.copy();
        this.chooseUseText = effect.chooseUseText;
        this.optional = effect.optional;
    }

    public DoIfCostPaid addEffect(Effect effect) {
        executingEffects.add(effect);
        return this;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getPayingPlayer(game, source);
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            String message;
            if (chooseUseText == null) {
                String effectText = executingEffects.getText(source.getModes().getMode());
                if (!effectText.isEmpty() && effectText.charAt(effectText.length() - 1) == '.') {
                    effectText = effectText.substring(0, effectText.length() - 1);
                }
                message = getCostText() + " and " + effectText + '?';
                message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
                CardUtil.replaceSourceName(message, mageObject.getName());
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, mageObject.getLogName());
            boolean result = true;
            if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                    && executingEffects.size() > 0 && (!optional || player.chooseUse(executingEffects.get(0).getOutcome(), message, source, game))) {
                cost.clearPaid();
                int bookmark = game.bookmarkState();
                if (cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    for (Effect effect : executingEffects) {
                        effect.setTargetPointer(this.targetPointer);
                        if (effect instanceof OneShotEffect) {
                            result &= effect.apply(game, source);
                        } else {
                            game.addEffect((ContinuousEffect) effect, source);
                        }
                    }
                    player.resetStoredBookmark(game); // otherwise you can e.g. undo card drawn with Mentor of the Meek
                } else {
                    // Paying cost was cancels so try to undo payment so far
                    game.restoreState(bookmark, DoIfCostPaid.class.getName());
                    if (!otherwiseEffects.isEmpty()) {
                        for (Effect effect : otherwiseEffects) {
                            effect.setTargetPointer(this.targetPointer);
                            if (effect instanceof OneShotEffect) {
                                result &= effect.apply(game, source);
                            } else {
                                game.addEffect((ContinuousEffect) effect, source);
                            }
                        }
                    }
                }
            } else if (!otherwiseEffects.isEmpty()) {
                for (Effect effect : otherwiseEffects) {
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

    public Cost getCost() {
        return cost;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return (optional ? "you may " : "") + getCostText() + ". If you do, " + executingEffects.getText(mode) + (!otherwiseEffects.isEmpty() ? " If you don't, " + otherwiseEffects.getText(mode) : "");
    }

    protected String getCostText() {
        StringBuilder sb = new StringBuilder();
        String costText = cost.getText();
        if (costText != null
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("put")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("return")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("exile")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("discard")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("sacrifice")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("remove")
                && !costText.toLowerCase(Locale.ENGLISH).startsWith("pay")) {
            sb.append("pay ");
        }
        return sb.append(costText).toString();
    }

    @Override
    public DoIfCostPaid copy() {
        return new DoIfCostPaid(this);
    }
}
