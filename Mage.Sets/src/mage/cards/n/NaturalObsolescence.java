
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class NaturalObsolescence extends CardImpl {

    public NaturalObsolescence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Put target artifact on the bottom of its owner's library.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
    }

    private NaturalObsolescence(final NaturalObsolescence card) {
        super(card);
    }

    @Override
    public NaturalObsolescence copy() {
        return new NaturalObsolescence(this);
    }
}
