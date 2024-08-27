

package mage.cards.m;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class MeltTerrain extends CardImpl {

    public MeltTerrain (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Destroy target land. Melt Terrain deals 2 damage to that land's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(2, "land"));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private MeltTerrain(final MeltTerrain card) {
        super(card);
    }

    @Override
    public MeltTerrain copy() {
        return new MeltTerrain(this);
    }
}
