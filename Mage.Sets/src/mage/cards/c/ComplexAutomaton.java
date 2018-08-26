
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class ComplexAutomaton extends CardImpl {

    public ComplexAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you control seven or more permanents, return Complex Automaton to its owner's hand.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandSourceEffect(true), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(), ComparisonType.MORE_THAN, 6),
                "At the beginning of your upkeep, if you control seven or more permanents, return Complex Automaton to its owner's hand."));
    }

    public ComplexAutomaton(final ComplexAutomaton card) {
        super(card);
    }

    @Override
    public ComplexAutomaton copy() {
        return new ComplexAutomaton(this);
    }
}
