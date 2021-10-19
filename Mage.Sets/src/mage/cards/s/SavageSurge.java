
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SavageSurge extends CardImpl {

    public SavageSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Target creature gets +2/+2 until end of turn. Untap that creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setOutcome(Outcome.Benefit);
        effect.setText("Untap that creature");
        this.getSpellAbility().addEffect(effect);
    }

    private SavageSurge(final SavageSurge card) {
        super(card);
    }

    @Override
    public SavageSurge copy() {
        return new SavageSurge(this);
    }
}
