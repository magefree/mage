
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Regenerate extends CardImpl {

    public Regenerate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Regenerate(final Regenerate card) {
        super(card);
    }

    @Override
    public Regenerate copy() {
        return new Regenerate(this);
    }
}
