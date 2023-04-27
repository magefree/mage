
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public final class ReveredUnicorn extends CardImpl {

    public ReveredUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
        // When Revered Unicorn leaves the battlefield, you gain life equal to the number of age counters on it.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new GainLifeEffect(new CountersSourceCount(CounterType.AGE)), false));
    }

    private ReveredUnicorn(final ReveredUnicorn card) {
        super(card);
    }

    @Override
    public ReveredUnicorn copy() {
        return new ReveredUnicorn(this);
    }
}
