
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public final class HymnToTourach extends CardImpl {

    public HymnToTourach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Target player discards two cards at random.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private HymnToTourach(final HymnToTourach card) {
        super(card);
    }

    @Override
    public HymnToTourach copy() {
        return new HymnToTourach(this);
    }
}
