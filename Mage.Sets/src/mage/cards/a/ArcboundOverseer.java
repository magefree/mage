
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.events.GameEvent;

/**
 *
 * @author jonubuu
 */
public final class ArcboundOverseer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter;

    static {
        filter = new FilterControlledCreaturePermanent("creature with modular you control");
        filter.add(new AbilityPredicate(ModularAbility.class));
    }

    public ArcboundOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{8}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // At the beginning of your upkeep, put a +1/+1 counter on each creature with modular you control.
        this.addAbility(new OnEventTriggeredAbility(
                GameEvent.EventType.UPKEEP_STEP_PRE,
                "beginning of your upkeep",
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)));
        // Modular 6
        this.addAbility(new ModularAbility(this, 6));
    }

    private ArcboundOverseer(final ArcboundOverseer card) {
        super(card);
    }

    @Override
    public ArcboundOverseer copy() {
        return new ArcboundOverseer(this);
    }
}
