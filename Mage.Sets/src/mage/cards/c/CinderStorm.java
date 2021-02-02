
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author nigelzor
 */
public final class CinderStorm extends CardImpl {

    public CinderStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{R}");

        // Cinder Storm deals 7 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
    }

    private CinderStorm(final CinderStorm card) {
        super(card);
    }

    @Override
    public CinderStorm copy() {
        return new CinderStorm(this);
    }
}
