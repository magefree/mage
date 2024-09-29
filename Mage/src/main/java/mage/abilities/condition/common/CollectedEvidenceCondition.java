package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Checks if the spell was cast with the alternate collect evidence cost
 *
 * @author TheElk801
 */
public enum CollectedEvidenceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, CollectEvidenceAbility.COLLECT_EVIDENCE_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        // must use "used" instead "collected" because it can be visible as card hint on stack before real collect
        return "Evidence was used";
    }
}
