package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class GainLifeAllEffect extends OneShotEffect {

    private final int amount;
    private final TargetController targetController;

    public GainLifeAllEffect(int amount) {
        this(amount, TargetController.EACH_PLAYER);
    }

    public GainLifeAllEffect(int amount, TargetController targetController) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.targetController = targetController;
        staticText = makeRule(amount, targetController);
    }

    private GainLifeAllEffect(final GainLifeAllEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.targetController = effect.targetController;
    }

    @Override
    public GainLifeAllEffect copy() {
        return new GainLifeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : getPlayers(targetController, game, source)) {
            Optional.ofNullable(playerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.gainLife(amount, game, source));
        }
        return true;
    }

    private static Collection<UUID> getPlayers(TargetController targetController, Game game, Ability source) {
        switch (targetController) {
            case ANY:
            case EACH_PLAYER:
                return game.getState().getPlayersInRange(source.getControllerId(), game);
            case OPPONENT:
                return game.getOpponents(source.getControllerId());
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
    }

    private static String makeRule(int amount, TargetController targetController) {
        StringBuilder sb = new StringBuilder("each ");
        switch (targetController) {
            case ANY:
            case EACH_PLAYER:
                sb.append("player");
                break;
            case OPPONENT:
                sb.append("opponent");
                break;
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
        sb.append(" gains ");
        sb.append(amount);
        sb.append(" life");
        return sb.toString();
    }
}
