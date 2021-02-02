
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.PentaviteToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class Pentavus extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Pentavite");

    static {
        filter.add(SubType.PENTAVITE.getPredicate());
    }

    public Pentavus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Pentavus enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                "with five +1/+1 counters on it"));

        // {1}, Remove a +1/+1 counter from Pentavus: Create a 1/1 colorless Pentavite artifact creature token with flying.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PentaviteToken(), 1), new GenericManaCost(1));
        firstAbility.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        this.addAbility(firstAbility);

        // {1}, Sacrifice a Pentavite: Put a +1/+1 counter on Pentavus.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(secondAbility);
    }

    private Pentavus(final Pentavus card) {
        super(card);
    }

    @Override
    public Pentavus copy() {
        return new Pentavus(this);
    }
}
