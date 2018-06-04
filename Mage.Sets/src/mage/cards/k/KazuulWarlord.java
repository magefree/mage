
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class KazuulWarlord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Ally creatures you control");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public KazuulWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), true));
    }

    public KazuulWarlord(final KazuulWarlord card) {
        super(card);
    }

    @Override
    public KazuulWarlord copy() {
        return new KazuulWarlord(this);
    }
}
