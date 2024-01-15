package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class Thraximundar extends CardImpl {

    public Thraximundar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Thraximundar attacks, defending player sacrifices a creature.
        this.addAbility(new AttacksTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "defending player"),
                false, null, SetTargetPointer.PLAYER
        ));

        // Whenever a player sacrifices a creature, you may put a +1/+1 counter on Thraximundar.
        this.addAbility(new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_PERMANENT_CREATURE,
            TargetController.ANY, SetTargetPointer.NONE, true
        ));

    }

    private Thraximundar(final Thraximundar card) {
        super(card);
    }

    @Override
    public Thraximundar copy() {
        return new Thraximundar(this);
    }
}
