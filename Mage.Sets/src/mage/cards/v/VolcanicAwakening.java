
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class VolcanicAwakening extends CardImpl {

    public VolcanicAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        // Destroy target land.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        // Storm
        this.addAbility(new StormAbility());
    }

    private VolcanicAwakening(final VolcanicAwakening card) {
        super(card);
    }

    @Override
    public VolcanicAwakening copy() {
        return new VolcanicAwakening(this);
    }
}
