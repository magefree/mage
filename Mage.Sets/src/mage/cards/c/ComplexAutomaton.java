package mage.cards.c;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComplexAutomaton extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent("you control seven or more permanents"), ComparisonType.MORE_THAN, 6
    );

    public ComplexAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, if you control seven or more permanents, return Complex Automaton to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ReturnToHandSourceEffect(true))
                .withInterveningIf(condition).addHint(PermanentsYouControlHint.instance));
    }

    private ComplexAutomaton(final ComplexAutomaton card) {
        super(card);
    }

    @Override
    public ComplexAutomaton copy() {
        return new ComplexAutomaton(this);
    }
}
