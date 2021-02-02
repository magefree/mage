
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, BetaSteward_at_googlemail.com (Excommunicate)
 */
public final class Repel extends CardImpl {

    public Repel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Put target creature on top of its owner's library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
    }

    private Repel(final Repel card) {
        super(card);
    }

    @Override
    public Repel copy() {
        return new Repel(this);
    }
}
