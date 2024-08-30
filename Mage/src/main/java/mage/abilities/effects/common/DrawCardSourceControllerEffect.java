package mage.abilities.effects.common;

import mage.abilities.Ability;
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

    public DrawCardSourceControllerEffect(int amount) {
        this(amount, false);
    }

    public DrawCardSourceControllerEffect(int amount, boolean youDraw) {
        this(StaticValue.get(amount), youDraw);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount) {
        this(amount, false);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, ValuePhrasing phrasing) {
        this(amount, false, phrasing);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw) {
        this(amount, youDraw, ValuePhrasing.LEGACY);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw, ValuePhrasing phrasing) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        createStaticText(youDraw, phrasing);
    }

    protected DrawCardSourceControllerEffect(final DrawCardSourceControllerEffect effect) {
        super(effect);
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

    private void createStaticText(boolean youDraw, ValuePhrasing phrasing) {
        if (phrasing == ValuePhrasing.LEGACY){
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
            staticText = sb.toString();
            return;
        }
        StringBuilder sb = new StringBuilder(youDraw ? "you " : "");
        sb.append("draw");
        String value = " a";
        if (amount instanceof StaticValue) {
            value = " " + CardUtil.numberToText(((StaticValue)amount).getValue(), "a");
        } else if (phrasing == ValuePhrasing.X_IS || phrasing == ValuePhrasing.X_HIDDEN) {
            value = " X";
        } else if (phrasing == ValuePhrasing.EQUAL_TO) {
            value = "";
        } else if (amount instanceof MultipliedValue) {
            value = " " + ((MultipliedValue)amount).getMultiplierText();
        }
        sb.append(value).append(value.equals(" a") ? " card" : " cards");
        if (!(amount instanceof StaticValue)) {
            switch (phrasing) {
                case X_IS:
                    sb.append(", where X is ");
                    break;
                case X_HIDDEN:
                    // No additional text
                    break;
                case EQUAL_TO:
                    sb.append(" equal to ");
                    break;
                case FOR_EACH:
                    sb.append(" for each ");
                    break;
                default:
                    throw new IllegalArgumentException("ValuePhrasing enum not implemented: " + phrasing);
            }
            sb.append(amount.getMessage(phrasing));
        }
        staticText = sb.toString();
    }
}
