
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class FieryTemper extends CardImpl {

    public FieryTemper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");


        // Fiery Temper deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Madness {R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{R}")));
    }

    private FieryTemper(final FieryTemper card) {
        super(card);
    }

    @Override
    public FieryTemper copy() {
        return new FieryTemper(this);
    }
}
