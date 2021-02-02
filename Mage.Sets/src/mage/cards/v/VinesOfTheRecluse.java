
package mage.cards.v;

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
 * @author LevelX2
 */
public final class VinesOfTheRecluse extends CardImpl {

    public VinesOfTheRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +1/+2 and gains reach until end of turn.
        Effect effect = new BoostTargetEffect(1, 2, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+2");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains reach until end of turn");
        this.getSpellAbility().addEffect(effect);
        // Untap it.
        effect = new UntapTargetEffect();
        effect.setText("untap it");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private VinesOfTheRecluse(final VinesOfTheRecluse card) {
        super(card);
    }

    @Override
    public VinesOfTheRecluse copy() {
        return new VinesOfTheRecluse(this);
    }
}
