package mage.cards.r;

import mage.abilities.effects.common.DamageTargetAndAllControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
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
        this.getSpellAbility().addEffect(new DamageTargetAndAllControlledEffect(
                3, 1, StaticFilters.FILTER_PERMANENT_CREATURE));
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
