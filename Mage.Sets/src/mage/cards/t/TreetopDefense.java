
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 *
 * @author TheElk801
 */
public final class TreetopDefense extends CardImpl {

    public TreetopDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Cast Treetop Defense only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast this spell only during the declare attackers step and only if you've been attacked this step."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // Creatures you control gain reach until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(ReachAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private TreetopDefense(final TreetopDefense card) {
        super(card);
    }

    @Override
    public TreetopDefense copy() {
        return new TreetopDefense(this);
    }
}
