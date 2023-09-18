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
 * @author LevelX2
 */

public class MillCardsControllerEffect extends OneShotEffect {

    private final DynamicValue numberCards;

    public MillCardsControllerEffect(int numberCards) {
        this(StaticValue.get(numberCards));
    }

    public MillCardsControllerEffect(DynamicValue numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        setText();
    }

    private MillCardsControllerEffect(final MillCardsControllerEffect effect) {
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
        return controller != null && !controller.millCards(
                numberCards.calculate(game, source, this), source, game
        ).isEmpty();
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("mill ");
        String value = numberCards.toString();
        sb.append(CardUtil.numberToText(value, "a"));
        sb.append(value.equals("1") ? " card" : " cards");
        String message = numberCards.getMessage();
        if (!message.isEmpty()) {
            sb.append(value.equals("X") ? ", where X is " : " for each ");
            sb.append(message);
        }
        staticText = sb.toString();
    }
}
