package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
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
public class MillCardsTargetXEffect extends OneShotEffect {

    private final DynamicValue numberCards;

    public MillCardsTargetXEffect(int numberCards) {
        this(StaticValue.get(numberCards));
    }

    public MillCardsTargetXEffect(DynamicValue numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
    }

    public MillCardsTargetXEffect(final MillCardsTargetXEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
    }

    @Override
    public MillCardsTargetXEffect copy() {
        return new MillCardsTargetXEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.millCards(numberCards.calculate(game, source, this), source, game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (!mode.getTargets().isEmpty()) {
            sb.append("target ");
            sb.append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("that player");
        }
        sb.append(" mills ");
        String message = numberCards.getMessage();
        if (message.isEmpty()) {
            if (numberCards.toString().equals("1")) {
                sb.append("a card");
            } else {
                sb.append(CardUtil.numberToText(numberCards.toString()));
                sb.append(" cards");
            }
        } else {
            sb.append("X cards, where X is the number of ");
            sb.append(message);
        }
        return sb.toString();
    }
}
