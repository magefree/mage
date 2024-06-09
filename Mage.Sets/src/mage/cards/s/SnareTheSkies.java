
package mage.cards.s;

import java.util.UUID;
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
 * @author Loki
 */
public final class SnareTheSkies extends CardImpl {

    public SnareTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Target creature gets +1/+1 and gains reach until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                .setText("target creature gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains reach until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SnareTheSkies(final SnareTheSkies card) {
        super(card);
    }

    @Override
    public SnareTheSkies copy() {
        return new SnareTheSkies(this);
    }
}
