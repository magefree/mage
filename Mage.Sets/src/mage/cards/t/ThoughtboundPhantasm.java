package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SurveilTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class ThoughtboundPhantasm extends CardImpl {

    public ThoughtboundPhantasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever you surveil, put a +1/+1 counter on Thoughtbound Phantasm.
        this.addAbility(new SurveilTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // As long as Thoughtbound Phantasm has three or more +1/+1 counters on it, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalAsThoughEffect(
                        new CanAttackAsThoughItDidntHaveDefenderSourceEffect(
                                Duration.WhileOnBattlefield
                        ), new SourceHasCounterCondition(CounterType.P1P1, 3)
                ).setText("As long as {this} has three "
                        + "or more +1/+1 counters on it, "
                        + "it can attack as though it "
                        + "didn't have defender.")
        ));
    }

    private ThoughtboundPhantasm(final ThoughtboundPhantasm card) {
        super(card);
    }

    @Override
    public ThoughtboundPhantasm copy() {
        return new ThoughtboundPhantasm(this);
    }
}
