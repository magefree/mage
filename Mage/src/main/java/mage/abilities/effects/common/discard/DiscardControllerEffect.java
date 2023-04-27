package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
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
public class DiscardControllerEffect extends OneShotEffect {

    protected final DynamicValue amount;
    protected final boolean randomDiscard;

    public DiscardControllerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public DiscardControllerEffect(int amount, boolean randomDiscard) {
        this(StaticValue.get(amount), randomDiscard);
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
        Player player = game.getPlayer(source.getControllerId());
        return player != null && !player.discard(amount.calculate(game, source, this), randomDiscard, false, source, game).isEmpty();
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("discard ");
        String value = amount.toString();
        sb.append(CardUtil.numberToText(value, "a"));
        sb.append(value.equals("1") ? " card" : " cards");
        if (randomDiscard) {
            sb.append(" at random");
        }
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(value.equals("X") ? ", where X is " : " for each ");
            sb.append(message);
        }
        staticText = sb.toString();
    }
}
