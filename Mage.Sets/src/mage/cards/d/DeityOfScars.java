
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author Loki
 */
public final class DeityOfScars extends CardImpl {

    public DeityOfScars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}{B/G}{B/G}{B/G}{B/G}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Deity of Scars enters the battlefield with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)), "with two -1/-1 counters on it"));

        // {B/G}, Remove a -1/-1 counter from Deity of Scars: Regenerate Deity of Scars.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B/G}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.M1M1.createInstance()));
        this.addAbility(ability);
    }

    private DeityOfScars(final DeityOfScars card) {
        super(card);
    }

    @Override
    public DeityOfScars copy() {
        return new DeityOfScars(this);
    }
}
