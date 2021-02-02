
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public final class DrainTheWell extends CardImpl {

    public DrainTheWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B/G}{B/G}");


        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private DrainTheWell(final DrainTheWell card) {
        super(card);
    }

    @Override
    public DrainTheWell copy() {
        return new DrainTheWell(this);
    }
}
