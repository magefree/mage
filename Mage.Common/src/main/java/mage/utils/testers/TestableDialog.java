package mage.utils.testers;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

import java.util.List;

/**
 * Part of testable game dialogs
 * <p>
 * How to use:
 * - extends BaseTestableDialog
 * - implement showDialog
 * - create register with all possible sample dialogs
 * - call register in main runner's constructor
 *
 * @author JayDi85
 */
interface TestableDialog {

    String getGroup();

    String getName();

    String getDescription();

    List<String> showDialog(Player player, Ability source, Game game, Player opponent);

    void showResult(Player player, Game game, String result);
}
