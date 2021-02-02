
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class Swelter extends CardImpl {

    public Swelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Swelter deals 2 damage to each of two target creatures.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2, true, "each of two target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2));
    }

    private Swelter(final Swelter card) {
        super(card);
    }

    @Override
    public Swelter copy() {
        return new Swelter(this);
    }
}
