
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author wetterlicht
 */
public final class RebukingCeremony extends CardImpl {

    public RebukingCeremony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");

        // Put two target artifacts on top of their owners' libraries.
        getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        getSpellAbility().addTarget(new TargetArtifactPermanent(2));
    }

    private RebukingCeremony(final RebukingCeremony card) {
        super(card);
    }

    @Override
    public RebukingCeremony copy() {
        return new RebukingCeremony(this);
    }
}
