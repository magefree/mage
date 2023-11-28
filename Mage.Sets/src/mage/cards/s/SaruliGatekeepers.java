package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
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


public final class SaruliGatekeepers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(SubType.GATE.getPredicate());
    }

    private static final Condition gatesCondition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1);

    public SaruliGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Saruli Gatekeepers enters the battlefield, if you control two or more Gates, gain 7 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainLifeEffect(7)),
                gatesCondition,
                "When {this} enters the battlefield, if you control two or more Gates, you gain 7 life.")
                .addHint(new ConditionHint(gatesCondition, "You control two or more Gates")));
    }

    private SaruliGatekeepers(final SaruliGatekeepers card) {
        super(card);
    }

    @Override
    public SaruliGatekeepers copy() {
        return new SaruliGatekeepers(this);
    }

}
