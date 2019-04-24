package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Scuttlegator extends CardImpl {

    public Scuttlegator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G/U}{G/U}");

        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {6}{G/U}{G/U}: Adapt 3.
        this.addAbility(new AdaptAbility(3, "{6}{G/U}{G/U}"));

        // As long as Scuttlegator has a +1/+1 counter on it, it can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalAsThoughEffect(
                        new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                        new SourceHasCounterCondition(CounterType.P1P1)
                ).setText("As long as {this} has a +1/+1 counter on it, " +
                        "it can attack as though it didn't have defender.")
        ));
    }

    private Scuttlegator(final Scuttlegator card) {
        super(card);
    }

    @Override
    public Scuttlegator copy() {
        return new Scuttlegator(this);
    }
}
