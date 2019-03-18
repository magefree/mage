
package mage.abilities.common;

import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ruleModifying.CastOnlyDuringPhaseStepSourceEffect;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class CastOnlyDuringPhaseStepSourceAbility extends SimpleStaticAbility {

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase) {
        this(turnPhase, null, null);
    }

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase, Condition condition) {
        this(turnPhase, null, condition);
    }

    public CastOnlyDuringPhaseStepSourceAbility(PhaseStep phaseStep) {
        this(null, phaseStep, null);
    }

    public CastOnlyDuringPhaseStepSourceAbility(PhaseStep phaseStep, Condition condition) {
        this(null, phaseStep, condition);
    }

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase, PhaseStep phaseStep, Condition condition) {
        this(turnPhase, phaseStep, condition, null);
    }

    public CastOnlyDuringPhaseStepSourceAbility(TurnPhase turnPhase, PhaseStep phaseStep, Condition condition, String effectText) {
        super(Zone.ALL, new CastOnlyDuringPhaseStepSourceEffect(turnPhase, phaseStep, condition));
        this.setRuleAtTheTop(true);
        if (effectText != null) {
            getEffects().get(0).setText(effectText);
        }
    }

    private CastOnlyDuringPhaseStepSourceAbility(final CastOnlyDuringPhaseStepSourceAbility ability) {
        super(ability);
    }

    @Override
    public CastOnlyDuringPhaseStepSourceAbility copy() {
        return new CastOnlyDuringPhaseStepSourceAbility(this);
    }
}
