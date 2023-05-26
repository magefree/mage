package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class MeldwebStrider extends CardImpl {

    public MeldwebStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Meldweb Strider enters the battlefield with an oil counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                "with an oil counter on it"
        ));

        // Remove an oil counter from Meldweb Strider: It becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new AddCardTypeSourceEffect(
                        Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
                ).setText("it becomes an artifact creature until end of turn"),
                new RemoveCountersSourceCost(CounterType.OIL.createInstance())
        ));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private MeldwebStrider(final MeldwebStrider card) {
        super(card);
    }

    @Override
    public MeldwebStrider copy() {
        return new MeldwebStrider(this);
    }
}
