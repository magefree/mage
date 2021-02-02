
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class RejuvenationChamber extends CardImpl {

    public RejuvenationChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Fading 2
        this.addAbility(new FadingAbility(2, this));
        // {tap}: You gain 2 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new TapSourceCost()));
    }

    private RejuvenationChamber(final RejuvenationChamber card) {
        super(card);
    }

    @Override
    public RejuvenationChamber copy() {
        return new RejuvenationChamber(this);
    }
}
