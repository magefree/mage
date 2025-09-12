package mage.utils.testers;

import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetPermanentOrPlayer;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
abstract class BaseTestableDialog implements TestableDialog {

    private Integer regNumber; // dialog number in runner (use it to find results and debugging)
    private final String group;
    private final String name;
    private final String description;
    private final TestableResult result;

    public BaseTestableDialog(String group, String name, String description, TestableResult result) {
        this.group = group;
        this.name = name;
        this.description = description;
        this.result = result;
    }

    @Override
    public void setRegNumber(Integer regNumber) {
        this.regNumber = regNumber;
    }

    @Override
    public Integer getRegNumber() {
        return this.regNumber;
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
    public void prepare() {
        this.result.onClear();
    }

    @Override
    final public void showResult(Player player, Game game) {
        // show message with result
        game.informPlayer(player, String.join("<br>", getResult().getResDetails()));
        // reset game and gui (in most use cases it must return to player's priority)
        game.firePriorityEvent(player.getId());
    }

    @Override
    public TestableResult getResult() {
        return this.result;
    }

    static Target createAnyTarget(int min, int max) {
        return createAnyTarget(min, max, false);
    }

    static Target createPlayerTarget(int min, int max, boolean notTarget) {
        return new TargetPlayer(min, max, notTarget);
    }

    private static Target createAnyTarget(int min, int max, boolean notTarget) {
        return new TargetPermanentOrPlayer(min, max).withNotTarget(notTarget);
    }

    private static final FilterPermanent impossibleFilter = new FilterPermanent();

    static {
        impossibleFilter.add(new ManaValuePredicate(ComparisonType.OR_LESS, -1));
    }

    static Target createImpossibleTarget(int min, int max) {
        return new TargetPermanent(min, max, impossibleFilter);
    }

    @Override
    public String toString() {
        return this.getGroup() + " - " + this.getName() + " - " + this.getDescription();
    }
}
