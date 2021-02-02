
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 * @author magenoxx_at_gmail.com
 */
public final class VolcanicHammer extends CardImpl {

    public VolcanicHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Volcanic Hammer deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private VolcanicHammer(final VolcanicHammer card) {
        super(card);
    }

    @Override
    public VolcanicHammer copy() {
        return new VolcanicHammer(this);
    }
}
