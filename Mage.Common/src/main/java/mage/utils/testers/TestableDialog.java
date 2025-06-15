package mage.utils.testers;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

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
public interface TestableDialog {

    void setRegNumber(Integer regNumber);

    Integer getRegNumber();

    String getGroup();

    String getName();

    String getDescription();

    TestableResult getResult();

    /**
     * Prepare dialog before show, e.g. clear prev results
     */
    void prepare();

    /**
     * Show game dialog to the user and save result
     */
    void showDialog(Player player, Ability source, Game game, Player opponent);

    /**
     * Show result dialog to the user
     */
    void showResult(Player player, Game game);
}
