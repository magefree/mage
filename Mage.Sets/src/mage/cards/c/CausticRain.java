
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public final class CausticRain extends CardImpl {

    public CausticRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private CausticRain(final CausticRain card) {
        super(card);
    }

    @Override
    public CausticRain copy() {
        return new CausticRain(this);
    }
}
