package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
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
public final class SkyclaveSentinel extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public SkyclaveSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {4}
        this.addAbility(new KickerAbility("{4}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // If Skyclave Sentinel was kicked, it enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), KickedCondition.ONCE,
                "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it.", ""
        ));

        // As long as Skyclave Sentinel has a +1/+1 counter on it, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield), condition
        ).setText("as long as {this} has a +1/+1 counter on it, it can attack as though it didn't have defender")));
    }

    private SkyclaveSentinel(final SkyclaveSentinel card) {
        super(card);
    }

    @Override
    public SkyclaveSentinel copy() {
        return new SkyclaveSentinel(this);
    }
}
