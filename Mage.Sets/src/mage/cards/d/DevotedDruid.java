
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class DevotedDruid extends CardImpl {

    public DevotedDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
        // Put a -1/-1 counter on Devoted Druid: Untap Devoted Druid.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new PutCountersSourceCost(CounterType.M1M1.createInstance())));
    }

    private DevotedDruid(final DevotedDruid card) {
        super(card);
    }

    @Override
    public DevotedDruid copy() {
        return new DevotedDruid(this);
    }
}
