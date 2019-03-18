
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Hex extends CardImpl {

    public Hex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Destroy six target creatures.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(6));
    }

    public Hex(final Hex card) {
        super(card);
    }

    @Override
    public Hex copy() {
        return new Hex(this);
    }
}
