package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class DiscardAndDrawThatManyEffect extends OneShotEffect {

    private final int amount;

    public DiscardAndDrawThatManyEffect(int amount) {
        super(Outcome.DrawCard);
        this.amount = amount;
        staticText = "discard "
                + (amount == Integer.MAX_VALUE ? "any number of" : "up to " + CardUtil.numberToText(amount))
                + " cards, then draw that many cards";
    }

    private DiscardAndDrawThatManyEffect(final DiscardAndDrawThatManyEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DiscardAndDrawThatManyEffect copy() {
        return new DiscardAndDrawThatManyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(player.discard(0, amount, false, source, game).size(), source, game);
        return true;
    }
}
