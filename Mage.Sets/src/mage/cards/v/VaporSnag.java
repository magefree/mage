
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class VaporSnag extends CardImpl {

    public VaporSnag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Return target creature to its owner's hand. Its controller loses 1 life.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private VaporSnag(final VaporSnag card) {
        super(card);
    }

    @Override
    public VaporSnag copy() {
        return new VaporSnag(this);
    }
}
