package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class CantGainLifeAllEffect extends ContinuousEffectImpl {

    private TargetController targetController;

    public CantGainLifeAllEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public CantGainLifeAllEffect(Duration duration) {
        this(duration, TargetController.ANY);
    }

    public CantGainLifeAllEffect(Duration duration, TargetController targetController) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.targetController = targetController;
        staticText = setText();

    }

    protected CantGainLifeAllEffect(final CantGainLifeAllEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
    }

    @Override
    public CantGainLifeAllEffect copy() {
        return new CantGainLifeAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        switch (targetController) {
            case YOU:
                controller.setCanGainLife(false);
                break;
            case NOT_YOU:
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && !player.equals(controller)) {
                        player.setCanGainLife(false);
                    }
                }
                break;
            case OPPONENT:
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (controller.hasOpponent(playerId, game)) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            player.setCanGainLife(false);
                        }
                    }
                }
                break;
            case ANY:
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.setCanGainLife(false);
                    }
                }
                break;
            case ENCHANTED:
                Optional
                        .ofNullable(source.getSourcePermanentIfItStillExists(game))
                        .map(Permanent::getAttachedTo)
                        .map(game::getPlayer)
                        .ifPresent(player -> player.setCanGainLife(false));
        }
        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("You");
                break;
            case NOT_YOU:
                sb.append("Other players");
                break;
            case OPPONENT:
                sb.append("Your opponents");
                break;
            case ANY:
                sb.append("Players");
                break;
            case ENCHANTED:
                sb.append("enchanted player");
        }
        sb.append(" can't gain life");
        if (!this.duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }

        }
        return sb.toString();
    }
}
