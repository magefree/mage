package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.hint.ConditionHint;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CaseSolvedHint extends ConditionHint {

    private final Condition condition;

    /**
     * Hint for use with CaseAbility
     * @param condition Same condition added to CaseAbility
     */
    public CaseSolvedHint(Condition condition) {
        super(SolvedSourceCondition.SOLVED, "Case is solved.", null, "Case is unsolved.", null, true);
        this.condition = condition;
    }

    protected CaseSolvedHint(final CaseSolvedHint hint) {
        super(hint);
        this.condition = hint.condition;
    }

    @Override
    public String getText(Game game, Ability ability) {
        Permanent permanent = game.getPermanent(ability.getSourceId());
        if (permanent == null) {
            return "";
        }
        String text = super.getText(game, ability);
        if (!permanent.isSolved()) {
            text += " " + getConditionText(game, ability);
            if (condition.apply(game, ability) && game.isActivePlayer(ability.getControllerId())) {
                text += " Case will be solved at the end step.";
            }
        }
        return text;
    }

    /**
     * Override to add specific information on satisfying the condition.
     */
    protected String getConditionText(Game game, Ability ability) {
        return "";
    }
}
