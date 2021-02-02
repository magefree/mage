
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class Devastate extends CardImpl {

    public Devastate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Destroy target land. Devastate deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageEverythingEffect(1));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private Devastate(final Devastate card) {
        super(card);
    }

    @Override
    public Devastate copy() {
        return new Devastate(this);
    }
}
