
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

import java.util.Objects;

/**
 *
 * @author LevelX2
 */
public class ModeChoiceSourceCondition implements Condition {

    private final String mode;

    public ModeChoiceSourceCondition(String mode) {
        this.mode = mode;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String chosenMode = (String) game.getState().getValue(source.getSourceId() + "_modeChoice");
        return chosenMode != null && chosenMode.equals(mode);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModeChoiceSourceCondition other = (ModeChoiceSourceCondition) obj;
        return Objects.equals(this.mode, other.mode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mode);
    }
}
