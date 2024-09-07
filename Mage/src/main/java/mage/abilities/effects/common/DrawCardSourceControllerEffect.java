package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.ValuePhrasing;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DrawCardSourceControllerEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected ValuePhrasing textPhrasing;
    protected boolean youDraw;
    protected boolean rulesTextAmountFirst = false;
    protected boolean additionalClause = false;

    public DrawCardSourceControllerEffect(int amount) {
        this(amount, false);
    }

    public DrawCardSourceControllerEffect(int amount, boolean youDraw) {
        this(StaticValue.get(amount), youDraw);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount) {
        this(amount, false);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, ValuePhrasing textPhrasing) {
        this(amount, false, textPhrasing);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw) {
        this(amount, youDraw, ValuePhrasing.LEGACY);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw, ValuePhrasing textPhrasing) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        this.youDraw = youDraw;
        this.textPhrasing = textPhrasing;
    }

    // with "for each" phrasings, this changes
    // "draw {N} card{s} for each {amount}"
    // to
    // "for each {amount}, draw {N} card{s}"
    public DrawCardSourceControllerEffect withAmountFirst() {
        this.rulesTextAmountFirst = true;
        return this;
    }

    // Use for phrasings like "draw an additional card" instead of "draw a card"
    public DrawCardSourceControllerEffect withAdditionalPhrasing() {
        this.additionalClause = true;
        return this;
    }

    protected DrawCardSourceControllerEffect(final DrawCardSourceControllerEffect effect) {
        super(effect);
        this.rulesTextAmountFirst = effect.rulesTextAmountFirst;
        this.additionalClause = effect.additionalClause;
        this.textPhrasing = effect.textPhrasing;
        this.youDraw = effect.youDraw;
        this.amount = effect.amount.copy();
    }

    @Override
    public DrawCardSourceControllerEffect copy() {
        return new DrawCardSourceControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null
                && player.canRespond()) {
            player.drawCards(amount.calculate(game, source, this), source, game);
            return true;
        }
        return false;
    }

    public String getText(Mode mode) {

        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        if (textPhrasing == ValuePhrasing.LEGACY){
            StringBuilder sb = new StringBuilder();
            if (youDraw){
                sb.append("you draw ");
            } else {
                sb.append("draw ");
            }
            String value = amount.toString();
            sb.append(CardUtil.numberToText(value, "a"));
            if (!value.contains("card")) {
                sb.append(value.equals("1") ? " card" : " cards");
            }
            String message = amount.getMessage();
            if (!message.isEmpty()) {
                sb.append(value.equals("X") ? ", where X is " : " for each ");
                sb.append(message);
            }
            return sb.toString();
        }

        // Currently handled phrasings:
        // draw {N} card{s} for each {amount}
        // draw an additional card for each {amount}
        // for each {amount}, draw {N} card{s}
        // draw cards equal to {amount}
        // draw X cards, where X is {amount}
        // draw X cards
        // draw that many cards

        String rulesText;
        switch (textPhrasing){
            case X_HIDDEN:
                rulesText = "draw X cards";
                break;
            case X_IS:
                rulesText = "draw X cards, where X is {amount}";
                break;
            case EQUAL_TO:
                rulesText = "draw cards equal to {amount}";
                break;
            case FOR_EACH:
                if (rulesTextAmountFirst){
                    rulesText = "for each {amount}, draw {N} card{s}";
                } else {
                    rulesText = "draw {N} card{s} for each {amount}";
                }
                break;
            default:
                throw new IllegalArgumentException("Phrasing " + textPhrasing + " is not supported in DrawCardSourceControllerEffect");
        }
        if (amount.getMessage(textPhrasing).equals("that many")) {
            rulesText = "draw that many cards";
        }

        if (youDraw){
            rulesText = rulesText.replace("draw", "you draw");
        }

        rulesText = rulesText.replace("{amount}", amount.getMessage(textPhrasing));

        // Generate replacement for {N} in "for each" phrasings
        String NString = "a";
        if (amount instanceof MultipliedValue){
            NString = ((MultipliedValue)amount).getMultiplierText("a");
        }

        // Handle static cases
        if (amount instanceof StaticValue){
            rulesText = "draw {N} card{s}";
            NString = CardUtil.numberToText(((StaticValue)amount).getValue(), "a");
        }

        // Detect plurality
        if (NString.equals("a")){
            rulesText = rulesText.replace("{s}", "");
        } else {
            rulesText = rulesText.replace("{s}", "s");
        }

        if (additionalClause){
            if (NString.equals("a")){
                NString = "an";
            }
            NString += " additional";
        }
        rulesText = rulesText.replace("{N}", NString);

        return rulesText;
    }
}
