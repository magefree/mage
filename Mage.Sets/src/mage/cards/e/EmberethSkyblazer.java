package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmberethSkyblazer extends CardImpl {

    public EmberethSkyblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // As long as it's your turn, Embereth Skyblazer has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "As long as it's your turn, {this} has flying."
        )).addHint(MyTurnHint.instance));

        // Whenever Embereth Skyblazer attacks, you may pay {2}{R}. If you do, creatures you control get +X/+0 until end of turn, where X is the number of opponents you have.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostControlledEffect(
                        OpponentsCount.instance, StaticValue.get(0), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE, false, true
                ).setText("creatures you control get +X/+0 until end of turn, where X is the number of opponents you have"),
                new ManaCostsImpl<>("{2}{R}")
        ), false));
    }

    private EmberethSkyblazer(final EmberethSkyblazer card) {
        super(card);
    }

    @Override
    public EmberethSkyblazer copy() {
        return new EmberethSkyblazer(this);
    }
}
