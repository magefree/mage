
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class DissentersDeliverance extends CardImpl {

    public DissentersDeliverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");


        // Destroy target artifact.
        getSpellAbility().addEffect(new DestroyTargetEffect());
        getSpellAbility().addTarget(new TargetArtifactPermanent());
        
        // Cycling {G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{G}")));

    }

    private DissentersDeliverance(final DissentersDeliverance card) {
        super(card);
    }

    @Override
    public DissentersDeliverance copy() {
        return new DissentersDeliverance(this);
    }
}
