
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class UnnaturalEndurance extends CardImpl {

    public UnnaturalEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Target creature gets +2/+0 until end of turn. Regenerate it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new RegenerateTargetEffect();
        effect.setText("Regenerate it");
        this.getSpellAbility().addEffect(effect);
    }

    private UnnaturalEndurance(final UnnaturalEndurance card) {
        super(card);
    }

    @Override
    public UnnaturalEndurance copy() {
        return new UnnaturalEndurance(this);
    }
}
