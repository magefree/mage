package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrondTheGatebreaker extends CardImpl {

    private static final Condition condition = new CompoundCondition(
            MyTurnCondition.instance, new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.ARMY))
    );

    public GrondTheGatebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As long as it's your turn and you control an Army, Grond, the Gatebreaker is an artifact creature.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new AddCardTypeSourceEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT, CardType.CREATURE),
                condition, "as long as it's your turn and you control an Army, {this} is an artifact creature"
        )));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private GrondTheGatebreaker(final GrondTheGatebreaker card) {
        super(card);
    }

    @Override
    public GrondTheGatebreaker copy() {
        return new GrondTheGatebreaker(this);
    }
}
