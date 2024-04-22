

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class TaintedStrike extends CardImpl {

    public TaintedStrike (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target creature gets +1/+0 and gains infect until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn)
                .setText("target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(InfectAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains infect until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TaintedStrike(final TaintedStrike card) {
        super(card);
    }

    @Override
    public TaintedStrike copy() {
        return new TaintedStrike(this);
    }

}
