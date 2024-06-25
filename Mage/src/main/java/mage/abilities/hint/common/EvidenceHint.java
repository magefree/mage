package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.hint.ConditionHint;
import mage.game.Game;

/**
 * @author JayDi85
 */
public class EvidenceHint extends ConditionHint {

    private final int needAmount;

    public EvidenceHint(int needAmount) {
        super(CollectedEvidenceCondition.instance, "evidence was collected");
        this.needAmount = needAmount;
    }

    private EvidenceHint(final EvidenceHint hint) {
        super(hint);
        this.needAmount = hint.needAmount;
    }

    @Override
    public String getText(Game game, Ability ability) {
        return String.format("%s (need: %d, can collect: %d)",
                super.getText(game, ability),
                this.needAmount,
                CollectEvidenceCost.getAvailableEvidence(ability.getControllerId(), game)
        );
    }

    @Override
    public EvidenceHint copy() {
        return new EvidenceHint(this);
    }
}
