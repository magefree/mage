

package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class MarbleChalice extends CardImpl {

    public MarbleChalice (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}{W}");

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new TapSourceCost()));
    }

    private MarbleChalice(final MarbleChalice card) {
        super(card);
    }

    @Override
    public MarbleChalice copy() {
        return new MarbleChalice(this);
    }
}
