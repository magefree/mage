
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Electrify extends CardImpl {

    public Electrify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Electrify deals 4 damage to target creature.
        getSpellAbility().addEffect(new DamageTargetEffect(4));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Electrify(final Electrify card) {
        super(card);
    }

    @Override
    public Electrify copy() {
        return new Electrify(this);
    }
}
