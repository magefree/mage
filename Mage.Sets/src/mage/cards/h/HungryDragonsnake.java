
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HungryDragonsnake extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("under an opponents's control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public HungryDragonsnake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature enters the battlefield under an opponents's control, put a +1/+1 counter on Hungry Dragonsnake.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, false
        ));
    }

    private HungryDragonsnake(final HungryDragonsnake card) {
        super(card);
    }

    @Override
    public HungryDragonsnake copy() {
        return new HungryDragonsnake(this);
    }
}
