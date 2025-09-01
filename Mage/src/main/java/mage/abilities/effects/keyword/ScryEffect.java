package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com, Susucr
 */
public class ScryEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected final boolean showEffectHint;

    public ScryEffect(int scryNumber) {
        this(scryNumber, true);
    }

    public ScryEffect(DynamicValue amount) {
        this(amount, false);
    }

    public ScryEffect(int scryNumber, boolean showEffectHint) {
        this(StaticValue.get(scryNumber), showEffectHint);
    }

    public ScryEffect(DynamicValue amount, boolean showEffectHint) {
        super(Outcome.Benefit);
        this.amount = amount.copy();
        this.showEffectHint = showEffectHint;
        this.staticText = generateText();
    }

    protected ScryEffect(final ScryEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.showEffectHint = effect.showEffectHint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.scry(this.amount.calculate(game, source, this), source, game);
    }

    @Override
    public ScryEffect copy() {
        return new ScryEffect(this);
    }

    private String generateText() {
        StringBuilder sb = new StringBuilder("scry ");
        String value = amount.toString();
        sb.append(value);
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(value.equals("X") ? ", where X is " : " for each ");
            sb.append(message);
        }
        if (showEffectHint) {
            if (value == "1") {
                sb.append(". <i>(Look at the top card of your library. You may put that card on the bottom.)</i>");
            } else {
                sb.append(". <i>(Look at the top ");
                sb.append(CardUtil.numberToText(value));
                sb.append(" cards of your library, then put any number of them on the bottom and the rest on top in any order.)</i>");
            }
        }
        return sb.toString();
    }
}
