package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class BrazenBlademaster extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_PERMANENT_ARTIFACT, ComparisonType.MORE_THAN, 1, true);

    public BrazenBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Brazen Blademaster attacks while you control two or more artifacts, it gets +2/+1 until end of turn.
        this.addAbility(new ConditionalTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(2, 1, Duration.EndOfTurn), false),
                condition,
                "Whenever {this} attacks while you control two or more artifacts, it gets +2/+1 until end of turn."
        ));
    }

    private BrazenBlademaster(final BrazenBlademaster card) {
        super(card);
    }

    @Override
    public BrazenBlademaster copy() {
        return new BrazenBlademaster(this);
    }
}
