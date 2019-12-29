package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMagicMirror extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.KNOWLEDGE);

    public TheMagicMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}{U}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);

        // This spell costs {1} less to cast for each instant and sorcery card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(
                StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY
        )).setRuleAtTheTop(true));

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // At the beginning of your upkeep, put a knowledge counter on The Magic Mirror, then draw a card for each knowledge counter on The Magic Mirror.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.KNOWLEDGE.createInstance())
                        .setText("put a knowledge counter on {this},"),
                TargetController.YOU, false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(xValue).concatBy("then"));
        this.addAbility(ability);
    }

    private TheMagicMirror(final TheMagicMirror card) {
        super(card);
    }

    @Override
    public TheMagicMirror copy() {
        return new TheMagicMirror(this);
    }
}
