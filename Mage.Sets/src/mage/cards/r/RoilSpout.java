
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RoilSpout extends CardImpl {

    public RoilSpout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{U}");

        // Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Awaken 4-{4}{W}{U}
        this.addAbility(new AwakenAbility(this, 4, "{4}{W}{U}"));
    }

    private RoilSpout(final RoilSpout card) {
        super(card);
    }

    @Override
    public RoilSpout copy() {
        return new RoilSpout(this);
    }
}
