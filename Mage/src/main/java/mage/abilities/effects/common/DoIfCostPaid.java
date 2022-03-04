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
    protected Effects otherwiseEffects = new Effects(); // used for Imprison
    protected final Cost cost;
    private final String chooseUseText;
    private final boolean optional;

    public DoIfCostPaid(Effect effectOnPaid, Cost cost) {
        this(effectOnPaid, cost, null);
    }

    public DoIfCostPaid(Effect effectOnPaid, Effect effectOnNotPaid, Cost cost) {
        this(effectOnPaid, effectOnNotPaid, cost, true);
    }

    public DoIfCostPaid(Effect effectOnPaid, Effect effectOnNotPaid, Cost cost, boolean optional) {
        this(effectOnPaid, cost, null, optional);
        if (effectOnNotPaid != null) {
            this.otherwiseEffects.add(effectOnNotPaid);
        }
    }

    public DoIfCostPaid(Effect effectOnPaid, Cost cost, String chooseUseText) {
        this(effectOnPaid, cost, chooseUseText, true);
    }

    public DoIfCostPaid(Effect effectOnPaid, Cost cost, String chooseUseText, boolean optional) {
        super(Outcome.Benefit);
        if (effectOnPaid != null) {
            this.executingEffects.add(effectOnPaid);
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

    public DoIfCostPaid addOtherwiseEffect(Effect effect) {
        otherwiseEffects.add(effect);
        return this;
    }

    public Effects getExecutingEffects() {
        return this.executingEffects;
    }

    public Effects getOtherwiseEffects() {
        return this.otherwiseEffects;
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
                message = CardUtil.addCostVerb(cost.getText()) + (effectText.isEmpty() ? "" : " and " + effectText) + "?";
                message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
            } else {
                message = chooseUseText;
            }
            message = CardUtil.replaceSourceName(message, mageObject.getName());
            boolean result = true;
            Outcome payOutcome = executingEffects.getOutcome(source, this.outcome);
            if (cost.canPay(source, source, player.getId(), game)
                    && (!optional || player.chooseUse(payOutcome, message, source, game))) {
                cost.clearPaid();
                int bookmark = game.bookmarkState();
                if (cost.pay(source, game, source, player.getId(), false)) {
                    game.informPlayers(player.getLogName() + " paid for " + mageObject.getLogName() + " - " + message);
                    if (!executingEffects.isEmpty()) {
                        for (Effect effect : executingEffects) {
                            effect.setTargetPointer(this.targetPointer);
                            if (effect instanceof OneShotEffect) {
                                result &= effect.apply(game, source);
                            } else {
                                game.addEffect((ContinuousEffect) effect, source);
                            }
                        }
                    }
                    player.resetStoredBookmark(game); // otherwise you can e.g. undo card drawn with Mentor of the Meek
                } else {
                    // Paying cost was cancels so try to undo payment so far
                    player.restoreState(bookmark, DoIfCostPaid.class.getName(), game);
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
        return (optional ? "you may " : "")
                + CardUtil.addCostVerb(cost.getText())
                + ". If you do, "
                + executingEffects.getText(mode)
                + (!otherwiseEffects.isEmpty() ? " If you don't, " + otherwiseEffects.getText(mode) : "");
    }

    @Override
    public void setValue(String key, Object value) {
        super.setValue(key, value);
        this.executingEffects.setValue(key, value);
        this.otherwiseEffects.setValue(key, value);
    }

    @Override
    public DoIfCostPaid copy() {
        return new DoIfCostPaid(this);
    }

    public boolean isOptional() {
        return optional;
    }
}
