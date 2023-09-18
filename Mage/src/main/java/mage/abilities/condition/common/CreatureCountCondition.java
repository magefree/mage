package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author Styxo
 */
public class CreatureCountCondition implements Condition {

    private FilterCreaturePermanent filter;
    private int creatureCount;
    private TargetController targetController;

    public CreatureCountCondition(FilterCreaturePermanent filter, int creatureCount, TargetController targetController) {
        this.filter = filter;
        this.creatureCount = creatureCount;
        this.targetController = targetController;
    }

    public CreatureCountCondition(int creatureCount, TargetController targetController) {
        this.filter = new FilterCreaturePermanent();
        this.creatureCount = creatureCount;
        this.targetController = targetController;

    }

    @Override
    public boolean apply(Game game, Ability source) {
        switch (targetController) {
            case YOU:
                return game.getBattlefield().countAll(filter, source.getControllerId(), game) == creatureCount;
            case OPPONENT:
                for (UUID opponent : game.getOpponents(source.getControllerId())) {
                    if (game.getBattlefield().countAll(filter, opponent, game) != creatureCount) {
                        return false;
                    }
                }
                return true;
            case ANY:
                return game.getBattlefield().count(filter, source.getControllerId(), source, game) == creatureCount;
            default:
                throw new UnsupportedOperationException("Value for targetController not supported: " + targetController.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case YOU:
                sb.append("you");
                break;
            case OPPONENT:
                sb.append("your opponents");
                break;
            case ANY:
                sb.append("if ");
                sb.append(creatureCount);
                sb.append(' ');
                sb.append(filter.getMessage());
                sb.append(" are on the battlefield");
                return sb.toString();
        }
        sb.append(" control");
        if (creatureCount == 0) {
            sb.append(" no ");
        } else {
            sb.append(" exactly ");
            sb.append(creatureCount);
            sb.append(' ');
        }
        sb.append(filter.getMessage());
        sb.append(creatureCount != 1 ? "s" : "");

        return sb.toString();
    }
}
