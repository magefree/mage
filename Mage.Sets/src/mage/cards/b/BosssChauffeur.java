package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AllianceAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BosssChauffeur extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            StaticValue.get(1),
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
    );
    private static final Hint hint = new ValueHint(
            "Other creatures you control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
    );
    private static final DynamicValue counterCount = new CountersSourceCount(CounterType.P1P1);

    public BosssChauffeur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Boss's Chauffeur enters the battlefield with a number of +1/+1 counters on it equal to one plus the number of other creatures you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), xValue, false
        ), "with a number of +1/+1 counters on it equal to " +
                "one plus the number of other creatures you control").addHint(hint));

        // Alliance — Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Boss's Chauffeur.
        this.addAbility(new AllianceAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // When Boss's Chauffeur dies, create a 1/1 green and white Citizen creature token for each +1/+1 counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(
                new CitizenGreenWhiteToken(), counterCount
        ).setText("create a 1/1 green and white Citizen creature token for each +1/+1 counter on it")));
    }

    private BosssChauffeur(final BosssChauffeur card) {
        super(card);
    }

    @Override
    public BosssChauffeur copy() {
        return new BosssChauffeur(this);
    }
}
