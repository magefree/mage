
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Downpour extends CardImpl {

    public Downpour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Tap up to three target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private Downpour(final Downpour card) {
        super(card);
    }

    @Override
    public Downpour copy() {
        return new Downpour(this);
    }
}
