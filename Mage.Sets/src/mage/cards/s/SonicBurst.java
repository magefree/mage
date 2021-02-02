
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author markedagain
 */
public final class SonicBurst extends CardImpl {

    public SonicBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // As an additional cost to cast Sonic Burst, discard a card at random.
        this.getSpellAbility().addCost(new DiscardCardCost(true));
        // Sonic Burst deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SonicBurst(final SonicBurst card) {
        super(card);
    }

    @Override
    public SonicBurst copy() {
        return new SonicBurst(this);
    }
}
