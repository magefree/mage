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

    protected final Effects executingEffects;
    protected final Effects otherwiseEffects;
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
        this(effectOnPaid, effectOnNotPaid, cost, null, optional);
    }

    public DoIfCostPaid(Effect effectOnPaid, Cost cost, String chooseUseText) {
        this(effectOnPaid, null, cost, chooseUseText, true);
    }

    public DoIfCostPaid(Effect effectOnPaid, Cost cost, String chooseUseText, boolean optional) {
        this(effectOnPaid, null, cost, chooseUseText, optional);
    }

    public DoIfCostPaid(Effect effectOnPaid, Effect effectOnNotPaid, Cost cost, String chooseUseText, boolean optional) {
        super(Outcome.Benefit);
        this.executingEffects = new Effects();
        this.otherwiseEffects = new Effects();
        if (effectOnPaid != null) {
            this.executingEffects.add(effectOnPaid);
        }
        if (effectOnNotPaid != null) {
            this.otherwiseEffects.add(effectOnNotPaid);
        }
        this.cost = cost;
        this.chooseUseText = chooseUseText;
        this.optional = optional;
    }

    protected DoIfCostPaid(final DoIfCostPaid effect) {
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

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getPayingPlayer(game, source);
        MageObject mageObject = game.getObject(source);
        if (player == null || mageObject == null) {
            return false;
        }
        String message = CardUtil.replaceSourceName(makeChooseText(source), mageObject.getName());
        Outcome payOutcome = executingEffects.getOutcome(source, this.outcome);
        boolean canPay = cost.canPay(source, source, player.getId(), game);
        boolean didPay = false;
        if (canPay && (!optional || player.chooseUse(payOutcome, message, source, game))) {
            cost.clearPaid();
            int bookmark = game.bookmarkState();
            if (cost.pay(source, game, source, player.getId(), false)) {
                didPay = true;
                game.informPlayers(player.getLogName() + " paid for " + mageObject.getLogName() + " - " + message);
                applyEffects(game, source, executingEffects);
                player.resetStoredBookmark(game); // otherwise you can e.g. undo card drawn with Mentor of the Meek
            } else {
                // Paying cost was cancels so try to undo payment so far
                player.restoreState(bookmark, DoIfCostPaid.class.getName(), game);
            }
        }
        if (!didPay) {
            // Not leaking the information in the game log that the player could
            // not actually pay the cost, in case it is an hidden one.
            game.informPlayers(player.getLogName() + " did not pay for " + mageObject.getLogName() + " - " + message);
            applyEffects(game, source, otherwiseEffects);
        }
        return true;
    }

    private void applyEffects(Game game, Ability source, Effects effects) {
        if (!effects.isEmpty()) {
            for (Effect effect : effects) {
                effect.setTargetPointer(this.getTargetPointer().copy());
                if (effect instanceof OneShotEffect) {
                    effect.apply(game, source);
                } else {
                    game.addEffect((ContinuousEffect) effect, source);
                }
            }
        }
    }

    private String makeChooseText(Ability source) {
        if (chooseUseText != null && !chooseUseText.isEmpty()) {
            return chooseUseText;
        }
        String message;
        String effectText = executingEffects.getText(source.getModes().getMode());
        if (!effectText.isEmpty() && effectText.charAt(effectText.length() - 1) == '.') {
            effectText = effectText.substring(0, effectText.length() - 1);
        }
        message = CardUtil.addCostVerb(cost.getText()) + (effectText.isEmpty() ? "" : " and " + effectText) + "?";
        message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
        return message;
    }

    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    public Cost getCost() {
        return cost;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return (optional ? "you may " : "")
                + CardUtil.addCostVerb(cost.getText()) + "."
                + (!executingEffects.isEmpty() ? " If you do, " + executingEffects.getText(mode) : "")
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
