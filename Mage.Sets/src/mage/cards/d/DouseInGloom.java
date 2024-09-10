
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class DouseInGloom extends CardImpl {

    public DouseInGloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
    }

    private DouseInGloom(final DouseInGloom card) {
        super(card);
    }

    @Override
    public DouseInGloom copy() {
        return new DouseInGloom(this);
    }
}
