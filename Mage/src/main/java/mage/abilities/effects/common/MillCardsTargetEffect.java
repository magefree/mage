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
 * @author BetaSteward_at_googlemail.com
 */
public class MillCardsTargetEffect extends OneShotEffect {

    private DynamicValue amount;

    public MillCardsTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public MillCardsTargetEffect(DynamicValue amount) {
        super(Outcome.Detriment);
        this.amount = amount;
    }

    public MillCardsTargetEffect(final MillCardsTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public MillCardsTargetEffect copy() {
        return new MillCardsTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.millCards(amount.calculate(game, source, this), source, game);
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
        String message = amount.getMessage();

        if (!mode.getTargets().isEmpty()) {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        } else {
            sb.append("that target");
        }

        sb.append(" mills ");
        if (message.isEmpty()) {
            if (amount.toString().equals("1")) {
                sb.append("a card");
            } else {
                sb.append(CardUtil.numberToText(amount.toString())).append(" cards");
            }
        } else {
            sb.append("X cards, where X is the number of ");
        }

        if (!message.isEmpty()) {
            sb.append(message);
        }
        return sb.toString();
    }
}
