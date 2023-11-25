
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class UncannySpeed extends CardImpl {

    public UncannySpeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Target creature gets +3/+0 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 0, Duration.EndOfTurn)
                .setText("target creature gets +3/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UncannySpeed(final UncannySpeed card) {
        super(card);
    }

    @Override
    public UncannySpeed copy() {
        return new UncannySpeed(this);
    }
}
