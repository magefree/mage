package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class Chlorophant extends CardImpl {

    public Chlorophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may put a +1/+1 counter on Chlorophant.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), TargetController.YOU, true
        ));

        // Threshold - As long as seven or more cards are in your graveyard, Chlorophant has "At the beginning of your upkeep, you may put another +1/+1 counter on Chlorophant."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new BeginningOfUpkeepTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                                .setText("put another +1/+1 counter on {this}"),
                        TargetController.YOU, true
                ), Duration.WhileOnBattlefield), ThresholdCondition.instance, "as long as seven or more cards " +
                "are in your graveyard, {this} has \"At the beginning of your upkeep, you may put another +1/+1 counter on {this}.\""
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private Chlorophant(final Chlorophant card) {
        super(card);
    }

    @Override
    public Chlorophant copy() {
        return new Chlorophant(this);
    }
}
