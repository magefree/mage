
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TidyConclusion extends CardImpl {

    public TidyConclusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{B}");

        // Destroy target creature. You gain 1 life for each artifact you control.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent())));
    }

    public TidyConclusion(final TidyConclusion card) {
        super(card);
    }

    @Override
    public TidyConclusion copy() {
        return new TidyConclusion(this);
    }
}
