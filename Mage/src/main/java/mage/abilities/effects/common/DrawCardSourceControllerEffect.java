package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
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
    protected String whoDrawCard = "";

    public DrawCardSourceControllerEffect(int amount) {
        this(amount, "");
    }

    public DrawCardSourceControllerEffect(int amount, String whoDrawCard) {
        this(new StaticValue(amount), whoDrawCard);
    }

    public DrawCardSourceControllerEffect(DynamicValue amount) {
        this(amount, "");
    }

    public DrawCardSourceControllerEffect(DynamicValue amount, String whoDrawCard) {
        super(Outcome.DrawCard);
        this.amount = amount.copy();
        this.whoDrawCard = whoDrawCard;
        setText();
    }

    public DrawCardSourceControllerEffect(final DrawCardSourceControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.whoDrawCard = effect.whoDrawCard;
        setText();
    }

    @Override
    public DrawCardSourceControllerEffect copy() {
        return new DrawCardSourceControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(amount.calculate(game, source, this), game);
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        boolean oneCard = (amount instanceof StaticValue && amount.calculate(null, null, this) == 1)
                || amount instanceof PermanentsOnBattlefieldCount || amount.toString().equals("1") || amount.toString().equals("a");
        sb.append(whoDrawCard.isEmpty() ? "" : whoDrawCard + " ").append("draw ").append(oneCard ? "a" : CardUtil.numberToText(amount.toString())).append(" card");
        if (!oneCard) {
            sb.append('s');
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
        }
        sb.append(message);
        staticText = sb.toString();
    }

}
