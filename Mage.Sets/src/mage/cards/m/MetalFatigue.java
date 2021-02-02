
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author djbrez
 */
public final class MetalFatigue extends CardImpl {
    

    public MetalFatigue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");

        // Tap all artifacts.
        this.getSpellAbility().addEffect(new TapAllEffect(new FilterArtifactPermanent("artifacts")));
    }

    private MetalFatigue(final MetalFatigue card) {
        super(card);
    }

    @Override
    public MetalFatigue copy() {
        return new MetalFatigue(this);
    }
}
