
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class ClearwaterGoblet extends CardImpl {

    public ClearwaterGoblet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // At the beginning of your upkeep, you may gain life equal to the number of charge counters on Clearwater Goblet.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(new CountersSourceCount(CounterType.CHARGE)), TargetController.YOU, true));
    }

    private ClearwaterGoblet(final ClearwaterGoblet card) {
        super(card);
    }

    @Override
    public ClearwaterGoblet copy() {
        return new ClearwaterGoblet(this);
    }
}
