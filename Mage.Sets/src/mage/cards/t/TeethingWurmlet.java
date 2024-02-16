package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TeethingWurmlet extends CardImpl {

    private static final PermanentsOnTheBattlefieldCondition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ARTIFACTS, ComparisonType.MORE_THAN, 2, true);

    public TeethingWurmlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Teething Wurmlet has deathtouch as long as you control three or more artifacts.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()), condition,
                "{this} has deathtouch as long as you control three or more artifacts"
        )));

        // Whenever an artifact enters the battlefield under your control, you gain 1 life. If this is the
        // first time this ability has resolved this turn, put a +1/+1 counter on Teething Wurmlet.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(1), StaticFilters.FILTER_PERMANENT_ARTIFACT_AN
        );
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.BoostCreature, 1, new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).setText("If this is the first time this ability has resolved this turn, put a +1/+1 counter on {this}"));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private TeethingWurmlet(final TeethingWurmlet card) {
        super(card);
    }

    @Override
    public TeethingWurmlet copy() {
        return new TeethingWurmlet(this);
    }
}
