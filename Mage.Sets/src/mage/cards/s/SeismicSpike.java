
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public final class SeismicSpike extends CardImpl {

    public SeismicSpike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Destroy target land. Add {R}{R}.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(2)));
    }

    private SeismicSpike(final SeismicSpike card) {
        super(card);
    }

    @Override
    public SeismicSpike copy() {
        return new SeismicSpike(this);
    }
}
