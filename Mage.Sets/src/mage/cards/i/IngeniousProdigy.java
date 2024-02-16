package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IngeniousProdigy extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public IngeniousProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Ingenious Prodigy enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // At the beginning of your upkeep, if Ingenious Prodigy has one or more +1/+1 counters on it, you may remove a +1/+1 counter from it. If you do, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new DoIfCostPaid(
                                new DrawCardSourceControllerEffect(1),
                                new RemoveCountersSourceCost(CounterType.P1P1.createInstance())
                        ), TargetController.YOU, false
                ), condition, "At the beginning of your upkeep, if {this} has one or more " +
                "+1/+1 counters on it, you may remove a +1/+1 counter from it. If you do, draw a card."
        ));
    }

    private IngeniousProdigy(final IngeniousProdigy card) {
        super(card);
    }

    @Override
    public IngeniousProdigy copy() {
        return new IngeniousProdigy(this);
    }
}
