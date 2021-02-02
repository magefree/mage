
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class BeaconOfDestruction extends CardImpl {

    public BeaconOfDestruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}{R}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private BeaconOfDestruction(final BeaconOfDestruction card) {
        super(card);
    }

    @Override
    public BeaconOfDestruction copy() {
        return new BeaconOfDestruction(this);
    }
}
