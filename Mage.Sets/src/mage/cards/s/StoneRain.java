

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class StoneRain extends CardImpl {

    public StoneRain (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_LAND));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private StoneRain(final StoneRain card) {
        super(card);
    }

    @Override
    public StoneRain copy() {
        return new StoneRain(this);
    }

}
