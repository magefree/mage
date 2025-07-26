package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DrawCardsEqualToDifferenceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoctorOctopusMasterPlanner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VILLAIN, "Villains");
    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 8);

    public DoctorOctopusMasterPlanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(8);

        // Other Villains you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter, true
        )));

        // Your maximum hand size is eight.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                8, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // At the beginning of your end step, if you have fewer than eight cards in hand, draw cards equal to the difference.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardsEqualToDifferenceEffect(8)).withInterveningIf(condition));
    }

    private DoctorOctopusMasterPlanner(final DoctorOctopusMasterPlanner card) {
        super(card);
    }

    @Override
    public DoctorOctopusMasterPlanner copy() {
        return new DoctorOctopusMasterPlanner(this);
    }
}
