
package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class BruteStrength extends CardImpl {

    public BruteStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Target creature gets +3/+1 and gain trample until end of turn.
        this.getSpellAbility().addEffect(new BoostGainAbilityGenericEffect(
                3, 1, Duration.EndOfTurn, TrampleAbility.getInstance()
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BruteStrength(final BruteStrength card) {
        super(card);
    }

    @Override
    public BruteStrength copy() {
        return new BruteStrength(this);
    }
}
