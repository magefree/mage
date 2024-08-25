package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
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

    public DrawCardSourceControllerEffect(DynamicValue amount, boolean youDraw) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        createStaticText(youDraw);
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

    private void createStaticText(boolean youDraw) {
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
    }
}
