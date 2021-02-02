
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
 * @author LoneFox
 */
public final class SonicSeizure extends CardImpl {

    public SonicSeizure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // As an additional cost to cast Sonic Seizure, discard a card at random.
        this.getSpellAbility().addCost(new DiscardCardCost(true));
        // Sonic Seizure deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private SonicSeizure(final SonicSeizure card) {
        super(card);
    }

    @Override
    public SonicSeizure copy() {
        return new SonicSeizure(this);
    }
}
