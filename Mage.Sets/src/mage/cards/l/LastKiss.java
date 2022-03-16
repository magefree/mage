

package mage.cards.l;

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
public final class LastKiss extends CardImpl {

    public LastKiss (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public LastKiss (final LastKiss card) {
        super(card);
    }

    @Override
    public LastKiss copy() {
        return new LastKiss(this);
    }

}
