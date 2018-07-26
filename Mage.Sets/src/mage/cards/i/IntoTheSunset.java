
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.ExileAndGainLifeEqualToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author EikePeace
 */
public final class IntoTheSunset extends CardImpl {

    public IntoTheSunset(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Exile target creature. Its controller gains life equal to its power.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileAndGainLifeEqualToughnessTargetEffect());

    }

    public IntoTheSunset(final IntoTheSunset card) {
        super(card);
    }

    @Override
    public IntoTheSunset copy() {
        return new IntoTheSunset(this);
    }
}
