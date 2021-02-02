
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class AimHigh extends CardImpl {

    public AimHigh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Untap target creature.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap target creature");
        this.getSpellAbility().addEffect(effect);

        // It gets +2/+2 and gains reach until of turn.
        effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("It gets +2/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains reach until end of turn");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AimHigh(final AimHigh card) {
        super(card);
    }

    @Override
    public AimHigh copy() {
        return new AimHigh(this);
    }
}
