
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author Backfir3
 */
public final class DesertTwister extends CardImpl {

    public DesertTwister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}{G}");


        // Destroy target permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
		this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private DesertTwister(final DesertTwister card) {
        super(card);
    }

    @Override
    public DesertTwister copy() {
        return new DesertTwister(this);
    }
}
