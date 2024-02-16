
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComplexAutomaton extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT, ComparisonType.MORE_THAN, 6
    );

    public ComplexAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you control seven or more permanents, return Complex Automaton to its owner's hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new ReturnToHandSourceEffect(true),
                        TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, " +
                "if you control seven or more permanents, return {this} to its owner's hand."
        ).addHint(new ValueHint("Permanents you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent()))));
    }

    private ComplexAutomaton(final ComplexAutomaton card) {
        super(card);
    }

    @Override
    public ComplexAutomaton copy() {
        return new ComplexAutomaton(this);
    }
}
