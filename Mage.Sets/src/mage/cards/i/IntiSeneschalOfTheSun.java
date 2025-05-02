package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntiSeneschalOfTheSun extends CardImpl {

    public IntiSeneschalOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you attack, you may discard a card. When you do, put a +1/+1 counter on target attacking creature. It gains trample until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("it gains trample until end of turn"));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoWhenCostPaid(
                ability, new DiscardCardCost(), "Discard a card?"
        ), 1));

        // Whenever you discard one or more cards, exile the top card of your library. You may play that card until your next end step.
        this.addAbility(new DiscardOneOrMoreCardsTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilYourNextEndStep)
                        .withTextOptions("that card", true)
        ));
    }

    private IntiSeneschalOfTheSun(final IntiSeneschalOfTheSun card) {
        super(card);
    }

    @Override
    public IntiSeneschalOfTheSun copy() {
        return new IntiSeneschalOfTheSun(this);
    }
}
