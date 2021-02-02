package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */


public final class OpalLakeGatekeepers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    private static final Condition gatesCondition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public OpalLakeGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Opal Lake Gatekeepers enters the battlefield, if you control two or more Gates, you may draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                gatesCondition,
                "When Opal Lake Gatekeepers enters the battlefield, if you control two or more Gates, you may draw a card.")
                .addHint(new ConditionHint(gatesCondition, "You control two or more Gates")));
    }

    private OpalLakeGatekeepers(final OpalLakeGatekeepers card) {
        super(card);
    }

    @Override
    public OpalLakeGatekeepers copy() {
        return new OpalLakeGatekeepers(this);
    }

}
