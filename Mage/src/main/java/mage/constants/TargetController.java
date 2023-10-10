package mage.constants;

import mage.cards.Card;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author North
 */
public enum TargetController {

    ACTIVE,
    ANY,
    YOU,
    NOT_YOU,
    OPPONENT,
    TEAM,
    OWNER,
    CONTROLLER_ATTACHED_TO,
    NEXT,
    EACH_PLAYER,
    ENCHANTED,
    SOURCE_TARGETS,
    MONARCH,
    SOURCE_CONTROLLER;

    private final OwnerPredicate ownerPredicate;
    private final PlayerPredicate playerPredicate;
    private final ControllerPredicate controllerPredicate;

    private TargetController() {
        this.ownerPredicate = new OwnerPredicate(this);
        this.playerPredicate = new PlayerPredicate(this);
        this.controllerPredicate = new ControllerPredicate(this);
    }

    public OwnerPredicate getOwnerPredicate() {
        return ownerPredicate;
    }

    public PlayerPredicate getPlayerPredicate() {
        return playerPredicate;
    }

    public ControllerPredicate getControllerPredicate() {
        return controllerPredicate;
    }

    public static class OwnerPredicate implements ObjectSourcePlayerPredicate<Card> {

        private final TargetController targetOwner;

        private OwnerPredicate(TargetController targetOwner) {
            this.targetOwner = targetOwner;
        }

        @Override
        public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
            Card card = input.getObject();
            UUID playerId = input.getPlayerId();
            if (card == null || playerId == null) {
                return false;
            }

            switch (targetOwner) {
                case YOU:
                    return card.isOwnedBy(playerId);
                case OPPONENT:
                    return !card.isOwnedBy(playerId)
                            && game.getPlayer(playerId).hasOpponent(card.getOwnerId(), game);
                case NOT_YOU:
                    return !card.isOwnedBy(playerId);
                case ENCHANTED:
                    Permanent permanent = input.getSource().getSourcePermanentIfItStillExists(game);
                    return permanent != null && input.getObject().isOwnedBy(permanent.getAttachedTo());
                case SOURCE_CONTROLLER:
                    return card.isOwnedBy(input.getSource().getControllerId());
                case SOURCE_TARGETS:
                    return card.isOwnedBy(input.getSource().getFirstTarget());
                case MONARCH:
                    return card.isOwnedBy(game.getMonarchId());
                case ANY:
                    return true;
                default:
                    throw new UnsupportedOperationException("TargetController not supported");
            }
        }

        @Override
        public String toString() {
            return "Owner(" + targetOwner + ')';
        }
    }

    public static class PlayerPredicate implements ObjectSourcePlayerPredicate<Player> {

        private final TargetController targetPlayer;

        private PlayerPredicate(TargetController player) {
            this.targetPlayer = player;
        }

        @Override
        public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
            Player player = input.getObject();
            UUID playerId = input.getPlayerId();
            if (player == null || playerId == null) {
                return false;
            }

            switch (targetPlayer) {
                case YOU:
                    return player.getId().equals(playerId);
                case OPPONENT:
                    return !player.getId().equals(playerId) &&
                            game.getPlayer(playerId).hasOpponent(player.getId(), game);
                case NOT_YOU:
                    return !player.getId().equals(playerId);
                case SOURCE_CONTROLLER:
                    return player.getId().equals(input.getSource().getControllerId());
                case SOURCE_TARGETS:
                    return player.getId().equals(input.getSource().getFirstTarget());
                case MONARCH:
                    return player.getId().equals(game.getMonarchId());
                default:
                    throw new UnsupportedOperationException("TargetController not supported");
            }
        }

        @Override
        public String toString() {
            return "Player(" + targetPlayer + ')';
        }
    }

    public static class ControllerPredicate implements ObjectSourcePlayerPredicate<Controllable> {

        private final TargetController controller;

        private ControllerPredicate(TargetController controller) {
            this.controller = controller;
        }

        @Override
        public boolean apply(ObjectSourcePlayer<Controllable> input, Game game) {
            Controllable object = input.getObject();
            UUID playerId = input.getPlayerId();

            switch (controller) {
                case YOU:
                    return object.isControlledBy(playerId);
                case TEAM:
                    return !game.getPlayer(playerId).hasOpponent(object.getControllerId(), game);
                case OPPONENT:
                    return !object.isControlledBy(playerId)
                            && game.getPlayer(playerId).hasOpponent(object.getControllerId(), game);
                case NOT_YOU:
                    return !object.isControlledBy(playerId);
                case ACTIVE:
                    return object.isControlledBy(game.getActivePlayerId());
                case ENCHANTED:
                    Permanent permanent = input.getSource().getSourcePermanentIfItStillExists(game);
                    return permanent != null && input.getObject().isControlledBy(permanent.getAttachedTo());
                case SOURCE_CONTROLLER:
                    return object.isControlledBy(input.getSource().getControllerId());
                case SOURCE_TARGETS:
                    return object.isControlledBy(input.getSource().getFirstTarget());
                case MONARCH:
                    return object.isControlledBy(game.getMonarchId());
                case ANY:
                    return true;
                default:
                    throw new UnsupportedOperationException("TargetController not supported");
            }
        }

        @Override
        public String toString() {
            return "TargetController(" + controller.toString() + ')';
        }
    }
}
