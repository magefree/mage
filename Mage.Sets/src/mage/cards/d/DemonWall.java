package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.MenaceAbility;
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
public final class DemonWall extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(null);

    public DemonWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // As long as this creature has a counter on it, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield), condition
        ).setText("as long as this creature has a counter on it, it can attack as though it didn't have defender")));

        // {5}{B}: Put two +1/+1 counters on this creature.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{5}{B}")
        ));
    }

    private DemonWall(final DemonWall card) {
        super(card);
    }

    @Override
    public DemonWall copy() {
        return new DemonWall(this);
    }
}
