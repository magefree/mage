package mage.constants;

import mage.cards.Card;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Controllable;
import mage.game.Game;
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
    SOURCE_TARGETS;

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

    public static class OwnerPredicate implements ObjectPlayerPredicate<ObjectPlayer<Card>> {

        private final TargetController targetOwner;

        private OwnerPredicate(TargetController targetOwner) {
            this.targetOwner = targetOwner;
        }

        @Override
        public boolean apply(ObjectPlayer<Card> input, Game game) {
            Card card = input.getObject();
            UUID playerId = input.getPlayerId();
            if (card == null || playerId == null) {
                return false;
            }

            switch (targetOwner) {
                case YOU:
                    if (card.isOwnedBy(playerId)) {
                        return true;
                    }
                    break;
                case OPPONENT:
                    if (!card.isOwnedBy(playerId)
                            && game.getPlayer(playerId).hasOpponent(card.getOwnerId(), game)) {
                        return true;
                    }
                    break;
                case NOT_YOU:
                    if (!card.isOwnedBy(playerId)) {
                        return true;
                    }
                    break;
                case ANY:
                    return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "Owner(" + targetOwner + ')';
        }
    }

    public static class PlayerPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

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
                    if (player.getId().equals(playerId)) {
                        return true;
                    }
                    break;
                case OPPONENT:
                    if (!player.getId().equals(playerId) &&
                            game.getPlayer(playerId).hasOpponent(player.getId(), game)) {
                        return true;
                    }
                    break;
                case NOT_YOU:
                    if (!player.getId().equals(playerId)) {
                        return true;
                    }
                    break;
            }

            return false;
        }

        @Override
        public String toString() {
            return "Player(" + targetPlayer + ')';
        }
    }

    public static class ControllerPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

        private final TargetController controller;

        private ControllerPredicate(TargetController controller) {
            this.controller = controller;
        }

        @Override
        public boolean apply(ObjectPlayer<Controllable> input, Game game) {
            Controllable object = input.getObject();
            UUID playerId = input.getPlayerId();

            switch (controller) {
                case YOU:
                    if (object.isControlledBy(playerId)) {
                        return true;
                    }
                    break;
                case TEAM:
                    if (!game.getPlayer(playerId).hasOpponent(object.getControllerId(), game)) {
                        return true;
                    }
                    break;
                case OPPONENT:
                    if (!object.isControlledBy(playerId)
                            && game.getPlayer(playerId).hasOpponent(object.getControllerId(), game)) {
                        return true;
                    }
                    break;
                case NOT_YOU:
                    if (!object.isControlledBy(playerId)) {
                        return true;
                    }
                    break;
                case ACTIVE:
                    if (object.isControlledBy(game.getActivePlayerId())) {
                        return true;
                    }
                    break;
                case ANY:
                    return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "TargetController(" + controller.toString() + ')';
        }
    }
}
