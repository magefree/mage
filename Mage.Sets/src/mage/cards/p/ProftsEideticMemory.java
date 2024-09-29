package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author DominionSpy
 */
public final class ProftsEideticMemory extends CardImpl {

    public ProftsEideticMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Proft's Eidetic Memory enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET)));

        // At the beginning of combat on your turn, if you've drawn more than one card this turn,
        // put X +1/+1 counters on target creature you control,
        // where X is the number of cards you've drawn this turn minus one.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new AddCountersTargetEffect(
                                CounterType.P1P1.createInstance(),
                                new IntPlusDynamicValue(-1, CardsDrawnThisTurnDynamicValue.instance)),
                        TargetController.YOU, false),
                DrewTwoOrMoreCardsCondition.instance,
                "At the beginning of combat on your turn, if you've drawn more than one card this turn, " +
                        "put X +1/+1 counters on target creature you control, " +
                        "where X is the number of cards you've drawn this turn minus one");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ProftsEideticMemory(final ProftsEideticMemory card) {
        super(card);
    }

    @Override
    public ProftsEideticMemory copy() {
        return new ProftsEideticMemory(this);
    }
}
