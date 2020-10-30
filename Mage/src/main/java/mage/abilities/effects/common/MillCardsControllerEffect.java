package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */

public class MillCardsControllerEffect extends OneShotEffect {

    private final int numberCards;

    public MillCardsControllerEffect(int numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.staticText = setText();
    }

    public MillCardsControllerEffect(final MillCardsControllerEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
    }

    @Override
    public MillCardsControllerEffect copy() {
        return new MillCardsControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return !controller.millCards(numberCards, source, game).isEmpty();
        }
        return false;
    }

    private String setText() {
        return "mill " + (numberCards == 1 ? "a card" : CardUtil.numberToText(numberCards) + " cards");
    }
}
