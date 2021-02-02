
package mage.cards.r;

import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author rscoates
 */
public final class RadiatingLightning extends CardImpl {

    public RadiatingLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // Radiating Lightning deals 3 damage to target player and 1 damage to each creature that player controls.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new DamageAllControlledTargetEffect(1, new FilterCreaturePermanent()).setText("and 1 damage to each creature that player controls"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private RadiatingLightning(final RadiatingLightning card) {
        super(card);
    }

    @Override
    public RadiatingLightning copy() {
        return new RadiatingLightning(this);
    }
}
