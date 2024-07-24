package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DrawCardSourceControllerEffect extends OneShotEffect {

    public enum DrawCardsPhrasing {
        EQUAL_TO,
        X_WHERE,
        FOR_EACH
    }

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

    public DrawCardSourceControllerEffect(DynamicValue amount, DrawCardsPhrasing phrasing) {
        this(amount, false, phrasing);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw) {
        this(amount, youDraw, DrawCardsPhrasing.FOR_EACH);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw, DrawCardsPhrasing phrasing) {
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

    private void createStaticText(boolean youDraw, DrawCardsPhrasing drawCardsPhrasing) {
        StringBuilder sb = new StringBuilder(youDraw ? "you " : "");
        sb.append("draw");
        String value = " a";
        if (amount instanceof StaticValue) {
            value = " " + CardUtil.numberToText(((StaticValue)amount).getValue(), "a");
        } else if (drawCardsPhrasing == DrawCardsPhrasing.X_WHERE) {
            value = " X";
        } else if (drawCardsPhrasing == DrawCardsPhrasing.EQUAL_TO) {
            value = "";
        } else if (amount instanceof MultipliedValue) {
            value = " " + ((MultipliedValue)amount).getMultiplierText();
        }
        sb.append(value).append(value.equals(" a") ? " card" : " cards");
        if (!(amount instanceof StaticValue)) {
            switch (drawCardsPhrasing) {
                case X_WHERE:
                    sb.append(", where X is ").append(amount.getMessage(DynamicValue.Phrasing.NUMBER_OF));
                    break;
                case EQUAL_TO:
                    sb.append(" equal to ").append(amount.getMessage(DynamicValue.Phrasing.NUMBER_OF));
                    break;
                case FOR_EACH:
                    sb.append(" for each ").append(amount.getMessage(DynamicValue.Phrasing.FOR_EACH));
                    break;
                default:
                    throw new IllegalArgumentException("drawCardsPhrasing enum not implemented: " + drawCardsPhrasing);
            }
        }
        staticText = sb.toString();
    }
}
