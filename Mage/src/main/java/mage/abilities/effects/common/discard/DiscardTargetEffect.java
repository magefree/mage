
package mage.abilities.effects.common.discard;

import java.util.UUID;
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
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DiscardTargetEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean randomDiscard;

    public DiscardTargetEffect(DynamicValue amount) {
        this(amount, false);
    }

    public DiscardTargetEffect(DynamicValue amount, boolean randomDiscard) {
        super(Outcome.Discard);
        this.randomDiscard = randomDiscard;
        this.amount = amount;
    }

    public DiscardTargetEffect(int amount) {
        this(StaticValue.get(amount));
    }

    /**
     *
     * @param amount amount of cards to discard
     * @param randomDiscard discard the cards by random
     *
     */
    public DiscardTargetEffect(int amount, boolean randomDiscard) {
        super(Outcome.Discard);
        this.randomDiscard = randomDiscard;
        this.amount = StaticValue.get(amount);
    }

    public DiscardTargetEffect(final DiscardTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.randomDiscard = effect.randomDiscard;
    }

    @Override
    public DiscardTargetEffect copy() {
        return new DiscardTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetPlayerId : targetPointer.getTargets(game, source)) {
            Player player = game.getPlayer(targetPlayerId);
            if (player != null) {
                player.discard(amount.calculate(game, source, this), randomDiscard, false, source, game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            sb.append("that player");
        } else {
            sb.append("target ").append(mode.getTargets().get(0).getTargetName());
        }
        sb.append(" discards ");
        if (amount.toString().equals("1") || amount.toString().equals("a")) {
            sb.append("a card");
        } else {
            sb.append(CardUtil.numberToText(amount.toString())).append(" cards");
        }
        if (randomDiscard) {
            sb.append(" at random");
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
        }
        sb.append(message);
        return sb.toString();
    }
}
