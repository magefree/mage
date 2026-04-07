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
    private final int plus;

    public DiscardAndDrawThatManyEffect(int amount) {
        this(amount, 0);
    }

    /**
     * @param amount maximum number of cards to discard, or {@link Integer#MAX_VALUE} for any number
     * @param plus   additional cards drawn beyond the number discarded
     */
    public DiscardAndDrawThatManyEffect(int amount, int plus) {
        super(Outcome.DrawCard);
        this.amount = amount;
        this.plus = plus;
        staticText = "discard "
                + (amount == Integer.MAX_VALUE ? "any number of" : "up to " + CardUtil.numberToText(amount))
                + " cards, then draw that many cards" + (plus > 0 ? " plus " + CardUtil.numberToText(plus) : "");
    }

    private DiscardAndDrawThatManyEffect(final DiscardAndDrawThatManyEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.plus = effect.plus;
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
        int discarded = player.discard(0, amount, false, source, game).size();
        player.drawCards(discarded + plus, source, game);
        return true;
    }
}
