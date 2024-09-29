

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseLifeSourceControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public LoseLifeSourceControllerEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeSourceControllerEffect(DynamicValue amount) {
        super(Outcome.LoseLife);
        this.amount = amount;
        setText();
    }

    protected LoseLifeSourceControllerEffect(final LoseLifeSourceControllerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifeSourceControllerEffect copy() {
        return new LoseLifeSourceControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.loseLife(amount.calculate(game, source, this), game, source, false);
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("you lose ").append(amount.toString()).append(" life");
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
        }
        sb.append(message);
        staticText = sb.toString();
    }

}
