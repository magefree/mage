
package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class GraspOfPhantoms extends CardImpl {

    public GraspOfPhantoms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {7}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{7}{U}")));
    }

    private GraspOfPhantoms(final GraspOfPhantoms card) {
        super(card);
    }

    @Override
    public GraspOfPhantoms copy() {
        return new GraspOfPhantoms(this);
    }
}
