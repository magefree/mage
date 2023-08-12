package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class TheMagicMirror extends CardImpl {

    public TheMagicMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}{U}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // This spell costs {1} less to cast for each instant and sorcery card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Instant and sorcery card in your graveyard", xValue));
        this.addAbility(ability);

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // At the beginning of your upkeep, put a knowledge counter on The Magic Mirror, then draw a card for each knowledge counter on The Magic Mirror.
        ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.KNOWLEDGE.createInstance())
                        .setText("put a knowledge counter on {this},"),
                TargetController.YOU, false);
        ability.addEffect(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.KNOWLEDGE)).concatBy("then"));
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
