
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author Loki
 */
public final class Oxidize extends CardImpl {

    public Oxidize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private Oxidize(final Oxidize card) {
        super(card);
    }

    @Override
    public Oxidize copy() {
        return new Oxidize(this);
    }
}
