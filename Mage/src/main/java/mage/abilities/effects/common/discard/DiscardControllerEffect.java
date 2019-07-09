
package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DiscardControllerEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean randomDiscard;

    public DiscardControllerEffect(int amount) {
        this(new StaticValue(amount));
    }

    public DiscardControllerEffect(int amount, boolean randomDiscard) {
        this(new StaticValue(amount), randomDiscard);
    }

    public DiscardControllerEffect(DynamicValue amount) {
        this(amount, false);
    }

    public DiscardControllerEffect(DynamicValue amount, boolean randomDiscard) {
        super(Outcome.Discard);
        this.amount = amount;
        this.randomDiscard = randomDiscard;
        setText();
    }

    public DiscardControllerEffect(final DiscardControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.randomDiscard = effect.randomDiscard;
    }

    @Override
    public DiscardControllerEffect copy() {
        return new DiscardControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (randomDiscard) {
                int maxAmount = Math.min(amount.calculate(game, source, this), player.getHand().size());
                for (int i = 0; i < maxAmount; i++) {
                    Card card = player.getHand().getRandom(game);
                    result |= player.discard(card, source, game);

                }
            } else {
                player.discard(amount.calculate(game, source, this), false, source, game);
                result = true;
            }
        }
        return result;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("discard ");
        if (amount.toString().equals("1")) {
            sb.append('a');
        } else {
            sb.append(CardUtil.numberToText(amount.toString()));
        }
        sb.append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append('s');
            }
        } catch (Exception e) {
            sb.append('s');
        }
        if (randomDiscard) {
            sb.append(" at random");
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
        }
        sb.append(message);
        staticText = sb.toString();
    }
}
