
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class MidnightBanshee extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public MidnightBanshee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(WitherAbility.getInstance());
        // At the beginning of your upkeep, put a -1/-1 counter on each nonblack creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersAllEffect(CounterType.M1M1.createInstance(), filter), TargetController.YOU, false));
    }

    public MidnightBanshee(final MidnightBanshee card) {
        super(card);
    }

    @Override
    public MidnightBanshee copy() {
        return new MidnightBanshee(this);
    }
}
