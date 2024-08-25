package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;

/**
 * @author xenohedron
 */
public class LifeCompareCondition implements Condition {

    private final TargetController targetController;
    private final ComparisonType comparisonType;
    private final int amount;

    /**
     * "As long as [player] has [number] or [more/less] life"
     * @param targetController YOU, OPPONENT, ANY, EACH_PLAYER
     * @param comparisonType comparison operator
     * @param amount life threshold
     */
    public LifeCompareCondition(TargetController targetController, ComparisonType comparisonType, int amount) {
        this.targetController = targetController;
        this.comparisonType = comparisonType;
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        switch (targetController) {
            case YOU:
                return ComparisonType.compare(controller.getLife(), comparisonType, amount);
            case OPPONENT:
                return game.getOpponents(controller.getId())
                        .stream()
                        .map(game::getPlayer)
                        .filter(Objects::nonNull)
                        .filter(Player::isInGame) // Once a player leaves the game, their life check is no longer valid.
                        .map(Player::getLife)
                        .anyMatch(l -> ComparisonType.compare(l, comparisonType, amount));
            case ANY:
                return game.getState().getPlayersInRange(controller.getId(), game)
                        .stream()
                        .map(game::getPlayer)
                        .filter(Objects::nonNull)
                        .filter(Player::isInGame)
                        .map(Player::getLife)
                        .anyMatch(l -> ComparisonType.compare(l, comparisonType, amount));
            case EACH_PLAYER:
                return game.getState().getPlayersInRange(controller.getId(), game)
                        .stream()
                        .map(game::getPlayer)
                        .filter(Objects::nonNull)
                        .filter(Player::isInGame)
                        .map(Player::getLife)
                        .allMatch(l -> ComparisonType.compare(l, comparisonType, amount));
            default:
                throw new IllegalArgumentException("Unsupported TargetController in LifeCompareCondition: " + targetController);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("you have ");
                break;
            case OPPONENT:
                sb.append("an opponent has ");
                break;
            case ANY:
                sb.append("a player has ");
                break;
            case EACH_PLAYER:
                sb.append("each player has ");
                break;
            default:
                throw new IllegalArgumentException("Unsupported TargetController in LifeCompareCondition: " + targetController);
        }
        switch (comparisonType) {
                case MORE_THAN:
                    sb.append("more than ").append(amount);
                    break;
                case FEWER_THAN:
                    sb.append("less than ").append(amount);
                    break;
                case EQUAL_TO:
                    sb.append("exactly ").append(amount);
                    break;
                case OR_GREATER:
                    sb.append(amount).append(" or more");
                    break;
                case OR_LESS:
                    sb.append(amount).append(" or less");
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported ComparisonType in LifeCompareCondition: " + comparisonType);
        }
        sb.append(" life");
        return sb.toString();
    }

}
