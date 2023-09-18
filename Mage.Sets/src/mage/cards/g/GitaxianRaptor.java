package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GitaxianRaptor extends CardImpl {

    public GitaxianRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Gitaxian Raptor enters the battlefield with three oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(3)),
                "with three oil counters on it"
        ));

        // Remove an oil counter from Gitaxian Raptor: Gitaxian Raptor gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, -1, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance())
        ));
    }

    private GitaxianRaptor(final GitaxianRaptor card) {
        super(card);
    }

    @Override
    public GitaxianRaptor copy() {
        return new GitaxianRaptor(this);
    }
}
