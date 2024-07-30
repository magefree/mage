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

    public DrawCardSourceControllerEffect(DynamicValue amount, DynamicValue.EffectPhrasing phrasing) {
        this(amount, false, phrasing);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw) {
        this(amount, youDraw, DynamicValue.EffectPhrasing.FOR_EACH);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw, DynamicValue.EffectPhrasing phrasing) {
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

    private void createStaticText(boolean youDraw, DynamicValue.EffectPhrasing phrasing) {
        StringBuilder sb = new StringBuilder(youDraw ? "you " : "");
        sb.append("draw");
        String value = " a";
        if (amount instanceof StaticValue) {
            value = " " + CardUtil.numberToText(((StaticValue)amount).getValue(), "a");
        } else if (phrasing == DynamicValue.EffectPhrasing.X_IS) {
            value = " X";
        } else if (phrasing == DynamicValue.EffectPhrasing.EQUAL_TO) {
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
                case EQUAL_TO:
                    sb.append(" equal to ");
                    break;
                case FOR_EACH:
                    sb.append(" for each ");
                    break;
                default:
                    throw new IllegalArgumentException("DynamicValue.EffectPhrasing enum not implemented: " + phrasing);
            }
            sb.append(amount.getMessage(phrasing));
        }
        staticText = sb.toString();
    }
}
