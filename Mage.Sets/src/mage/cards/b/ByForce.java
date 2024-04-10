
package mage.cards.b;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class ByForce extends CardImpl {

    public ByForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Destroy X target artifacts.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target artifacts"));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private ByForce(final ByForce card) {
        super(card);
    }

    @Override
    public ByForce copy() {
        return new ByForce(this);
    }
}
