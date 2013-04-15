package mage.abilities.effects.common;

import java.util.UUID;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;


public class DiscardEachPlayerEffect extends OneShotEffect<DiscardEachPlayerEffect> {

    protected DynamicValue amount;
    protected boolean randomDiscard;

    public DiscardEachPlayerEffect() {
        this(new StaticValue(1), false);
    }

    public DiscardEachPlayerEffect(int amount, boolean randomDiscard) {
        this(new StaticValue(amount), randomDiscard);
    }

    public DiscardEachPlayerEffect(DynamicValue amount, boolean randomDiscard) {
        super(Constants.Outcome.Discard);
        this.randomDiscard = randomDiscard;
        this.amount = amount;
    }

    public DiscardEachPlayerEffect(final DiscardEachPlayerEffect effect) {
        super(effect);
        this.randomDiscard = effect.randomDiscard;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (randomDiscard) {
                    int maxAmount = Math.min(amount.calculate(game, source), player.getHand().size());
                    for (int i = 0; i < maxAmount; i++) {
                        Card card = player.getHand().getRandom(game);
                        if (card != null) {
                            player.discard(card, source, game);
                        }
                    }
                } else {
                    player.discard(amount.calculate(game, source), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public DiscardEachPlayerEffect copy() {
        return new DiscardEachPlayerEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Each player discards ");
        sb.append(CardUtil.numberToText(amount.toString())).append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append("s");
            }
        } catch (Exception e) {
            sb.append("s");
        }
        if (randomDiscard) {
            sb.append(" at random");
        }
        return sb.toString();
    }

}
