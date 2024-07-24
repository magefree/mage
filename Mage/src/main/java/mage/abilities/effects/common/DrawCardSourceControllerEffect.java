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
        this(amount, "");
    }

    public DrawCardSourceControllerEffect(int amount, String whoDrawCard) {
        this(StaticValue.get(amount), whoDrawCard);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount) {
        this(amount, "");
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean XPhrasing) {
        this(amount, "", XPhrasing);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, String whoDrawCard) {
        this(amount, whoDrawCard, false);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, String whoDrawCard, boolean XPhrasing) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        createStaticText(whoDrawCard, XPhrasing);
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

    private void createStaticText(String whoDrawCard, boolean useX) {
        StringBuilder sb = new StringBuilder(whoDrawCard);
        sb.append(whoDrawCard.isEmpty() ? "draw " : " draw ");
        String value = "a";
        if (amount instanceof StaticValue) {
            value = CardUtil.numberToText(((StaticValue)amount).getValue(), "a");
        } else if (useX) {
            value = "X";
        } else if (amount instanceof MultipliedValue) {
            value = ((MultipliedValue)amount).getMultiplierText();
        }
        sb.append(value).append(value.equals("a") ? " card" : " cards");
        if (!(amount instanceof StaticValue)) {
            if (useX){
                sb.append(", where X is ").append(amount.getMessage(DynamicValue.Phrasing.NUMBER_OF));
            } else {
                sb.append(" for each ").append(amount.getMessage(DynamicValue.Phrasing.FOR_EACH));
            }
        }
        staticText = sb.toString();
    }
}
