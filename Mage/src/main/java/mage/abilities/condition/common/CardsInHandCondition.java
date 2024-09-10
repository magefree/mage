package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Cards in hand condition
 *
 * @author LevelX
 */
public class CardsInHandCondition implements Condition {

    private final ComparisonType type;
    private final int count;
    private final TargetController targetController;

    public CardsInHandCondition(ComparisonType type, int count) {
        this(type, count, TargetController.YOU);
    }

    public CardsInHandCondition(ComparisonType type, int count, TargetController targetController) {
        this.type = type;
        this.count = count;
        this.targetController = targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            switch (targetController) {
                case YOU:
                    conditionApplies = ComparisonType.compare(game.getPlayer(source.getControllerId()).getHand().size(), type, count);
                    break;
                case ACTIVE:
                    Player player = game.getPlayer(game.getActivePlayerId());
                    if (player != null) {
                        conditionApplies = ComparisonType.compare(player.getHand().size(), type, count);
                    }
                    break;
                case ANY:
                    for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                        player = game.getPlayer(playerId);
                        if (player != null) {
                            if (ComparisonType.compare(player.getHand().size(), type, this.count)) {
                                conditionApplies = true;
                            }
                        }
                    }
                    break;
                case EACH_PLAYER:
                    boolean conflict = false;
                    for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                        player = game.getPlayer(playerId);
                        if (player != null) {
                            if (!ComparisonType.compare(player.getHand().size(), type, this.count)) {
                                conflict = true;
                                break;
                            }
                        }
                    }
                    conditionApplies = !conflict;
                    break;
                default:
                    throw new UnsupportedOperationException("Value of TargetController not supported for this class.");
            }
        }

        return conditionApplies;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("if ");
        switch (targetController) {
            case YOU:
                sb.append("you have ");
                break;
            case ACTIVE:
                sb.append("that player has ");
                break;
            case EACH_PLAYER:
                sb.append("each player has ");
                break;
            case ANY:
                sb.append("a player has ");
                break;
        }
        switch (this.type) {
            case FEWER_THAN:
                sb.append(CardUtil.numberToText(count - 1));
                sb.append(" or fewer ");
                break;
            case MORE_THAN:
                sb.append(CardUtil.numberToText(count + 1));
                sb.append(" or more ");
                break;
            case OR_LESS:
                sb.append(CardUtil.numberToText(count));
                sb.append(" or fewer ");
                break;
            case OR_GREATER:
                sb.append(CardUtil.numberToText(count));
                sb.append(" or more ");
                break;
            case EQUAL_TO:
                if (count > 0) {
                    sb.append("exactly ");
                    sb.append(CardUtil.numberToText(count));
                    sb.append(" ");
                } else {
                    sb.append("no ");
                }
                break;
        }
        sb.append("cards in hand");
        return sb.toString();
    }
}
