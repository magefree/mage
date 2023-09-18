
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Vacuumelt extends CardImpl {

    public Vacuumelt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");


        // Replicate {2}{U}
        this.addAbility(new ReplicateAbility("{2}{U}"));
        // Return target creature to its owner's hand.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
    }

    private Vacuumelt(final Vacuumelt card) {
        super(card);
    }

    @Override
    public Vacuumelt copy() {
        return new Vacuumelt(this);
    }
}
