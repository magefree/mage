
package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author nigelzor
 */
public final class PhyrexianTribute extends CardImpl {

    public PhyrexianTribute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // As an additional cost to cast Phyrexian Tribute, sacrifice two creatures.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(2)));
        // Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private PhyrexianTribute(final PhyrexianTribute card) {
        super(card);
    }

    @Override
    public PhyrexianTribute copy() {
        return new PhyrexianTribute(this);
    }
}
