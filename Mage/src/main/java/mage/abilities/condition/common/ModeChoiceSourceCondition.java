
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

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
}
