
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AnchorToTheAether extends CardImpl {

    public AnchorToTheAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Put target creature on top of its owner's library. Scry 1.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ScryEffect(1));

    }

    private AnchorToTheAether(final AnchorToTheAether card) {
        super(card);
    }

    @Override
    public AnchorToTheAether copy() {
        return new AnchorToTheAether(this);
    }
}
