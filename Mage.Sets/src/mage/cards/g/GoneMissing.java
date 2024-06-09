
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class GoneMissing extends CardImpl {

    public GoneMissing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // Put target permanent on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Investigate.
        this.getSpellAbility().addEffect(new InvestigateEffect().concatBy("<br>"));
    }

    private GoneMissing(final GoneMissing card) {
        super(card);
    }

    @Override
    public GoneMissing copy() {
        return new GoneMissing(this);
    }
}
