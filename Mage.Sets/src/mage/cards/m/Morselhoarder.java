package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class Morselhoarder extends CardImpl {

    public Morselhoarder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/G}{R/G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Morselhoarder enters the battlefield with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), "with two -1/-1 counters on it"));

        // Remove a -1/-1 counter from Morselhoarder: Add one mana of any color.
        this.addAbility(new MorselhoarderAbility());

    }

    private Morselhoarder(final Morselhoarder card) {
        super(card);
    }

    @Override
    public Morselhoarder copy() {
        return new Morselhoarder(this);
    }
}

class MorselhoarderAbility extends ActivatedManaAbilityImpl {

    public MorselhoarderAbility() {
        this(new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
    }

    public MorselhoarderAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1, new CountersSourceCount(CounterType.M1M1), false), cost);
        this.netMana.add(new Mana(0, 0, 0, 0, 0, 0, 1, 0));
    }

    public MorselhoarderAbility(final MorselhoarderAbility ability) {
        super(ability);
    }

    @Override
    public MorselhoarderAbility copy() {
        return new MorselhoarderAbility(this);
    }
}
