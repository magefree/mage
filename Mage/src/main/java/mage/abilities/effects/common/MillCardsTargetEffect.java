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

import java.util.UUID;

/**
 * @author LevelX2
 */
public class MillCardsTargetEffect extends OneShotEffect {

    private final DynamicValue numberCards;

    public MillCardsTargetEffect(int numberCards) {
        this(StaticValue.get(numberCards));
    }

    public MillCardsTargetEffect(DynamicValue numberCards) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
    }

    protected MillCardsTargetEffect(final MillCardsTargetEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
    }

    @Override
    public MillCardsTargetEffect copy() {
        return new MillCardsTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getTargetPointer().getTargets(game, source)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.millCards(numberCards.calculate(game, source, this), source, game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "that player"));
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
