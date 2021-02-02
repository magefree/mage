
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class HarbingerOfNight extends CardImpl {

    public HarbingerOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, put a -1/-1 counter on each creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersAllEffect(CounterType.M1M1.createInstance(), new FilterCreaturePermanent()),
            TargetController.YOU, false));
    }

    private HarbingerOfNight(final HarbingerOfNight card) {
        super(card);
    }

    @Override
    public HarbingerOfNight copy() {
        return new HarbingerOfNight(this);
    }
}
