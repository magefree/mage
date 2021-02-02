
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class Disharmony extends CardImpl {

    public Disharmony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Cast Disharmony only during combat before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, BeforeBlockersAreDeclaredCondition.instance));

        // Untap target attacking creature and remove it from combat. Gain control of that creature until end of turn.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new RemoveFromCombatTargetEffect().setText("and remove it from combat."));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn, true).setText("Gain control of that creature until end of turn."));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private Disharmony(final Disharmony card) {
        super(card);
    }

    @Override
    public Disharmony copy() {
        return new Disharmony(this);
    }
}
