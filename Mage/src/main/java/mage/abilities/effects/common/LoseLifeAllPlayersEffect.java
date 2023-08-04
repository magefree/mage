
package mage.abilities.effects.common;

import java.util.UUID;

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
public class LoseLifeAllPlayersEffect extends OneShotEffect {

    private final DynamicValue amount;

    public LoseLifeAllPlayersEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeAllPlayersEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = setText();
    }

    public LoseLifeAllPlayersEffect(DynamicValue amount, String text) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = text;
    }

    protected LoseLifeAllPlayersEffect(final LoseLifeAllPlayersEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.loseLife(amount.calculate(game, source, this), game, source, false);
            }
        }
        return true;
    }

    @Override
    public LoseLifeAllPlayersEffect copy() {
        return new LoseLifeAllPlayersEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("each player loses ");
        sb.append(amount);
        sb.append(" life");
        return sb.toString();
    }

}
