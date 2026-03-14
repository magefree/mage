package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseLifeSourceControllerEffect extends OneShotEffect {

    protected DynamicValue amount;

    public LoseLifeSourceControllerEffect(int amount) {
        this(amount, true);
    }

    public LoseLifeSourceControllerEffect(int amount, boolean youLose) {
        this(StaticValue.get(amount), youLose);
    }

    public LoseLifeSourceControllerEffect(DynamicValue amount) {
        this(amount, true);
    }

    public LoseLifeSourceControllerEffect(DynamicValue amount, boolean youLose) {
        super(Outcome.LoseLife);
        this.amount = amount;
        setText(youLose);
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

    private void setText(boolean youLose) {
        StringBuilder sb = new StringBuilder();
        if (youLose) {
            sb.append("you ");
        }
        sb.append("lose ");
        sb.append(amount.toString());
        sb.append(" life");
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
        }
        sb.append(message);
        staticText = sb.toString();
    }

}
