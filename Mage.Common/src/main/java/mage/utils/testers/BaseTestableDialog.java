package mage.utils.testers;

import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPermanentOrPlayer;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
abstract class BaseTestableDialog implements TestableDialog {

    private final String group;
    private final String name;
    private final String description;

    public BaseTestableDialog(String group, String name, String description) {
        this.group = group;
        this.name = name;
        this.description = description;
    }

    @Override
    final public String getGroup() {
        return this.group;
    }

    @Override
    final public String getName() {
        return this.name;
    }

    @Override
    final public String getDescription() {
        return this.description;
    }

    @Override
    final public void showResult(Player player, Game game, String result) {
        // show message with result
        game.informPlayer(player, result);
        // reset game and gui (in most use cases it must return to player's priority)
        game.firePriorityEvent(player.getId());
    }

    static Target createAnyTarget(int min, int max) {
        return createAnyTarget(min, max, false);
    }

    private static Target createAnyTarget(int min, int max, boolean notTarget) {
        return new TargetPermanentOrPlayer(min, max).withNotTarget(notTarget);
    }

    static Target createCreatureTarget(int min, int max) {
        return createCreatureTarget(min, max, false);
    }

    private static Target createCreatureTarget(int min, int max, boolean notTarget) {
        return new TargetCreaturePermanent(min, max).withNotTarget(notTarget);
    }

    static Target createImpossibleTarget(int min, int max) {
        return createImpossibleTarget(min, max, false);
    }

    private static Target createImpossibleTarget(int min, int max, boolean notTarget) {
        return new TargetCreaturePermanent(min, max, new FilterCreaturePermanent(SubType.TROOPER, "rare type"), notTarget);
    }
}
