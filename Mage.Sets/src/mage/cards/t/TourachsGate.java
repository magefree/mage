package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class TourachsGate extends CardImpl {

    private static final FilterCreaturePermanent filterAttackingCreatures = new FilterCreaturePermanent("attacking creatures you control");
    private static final FilterPermanent filterUntapped = new FilterPermanent("enchanted land is untapped");
    private static final FilterControlledPermanent filterThrull = new FilterControlledPermanent(SubType.THRULL, "a Thrull");

    static {
        filterAttackingCreatures.add(AttackingPredicate.instance);
        filterAttackingCreatures.add(TargetController.YOU.getControllerPredicate());
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new SourceHasCounterCondition(CounterType.TIME, ComparisonType.EQUAL_TO, 0);
    private static final Condition condition2 = new AttachedToMatchesFilterCondition(filterUntapped);

    public TourachsGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant land you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Sacrifice a Thrull: Put three time counters on Tourach's Gate.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.TIME.createInstance(3)),
                new SacrificeTargetCost(filterThrull)
        ));

        // At the beginning of your upkeep, remove a time counter from Tourach's Gate. If there are no time counters on Tourach's Gate, sacrifice it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.TIME.createInstance())
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), condition,
                "If there are no time counters on {this}, sacrifice it"
        ));
        this.addAbility(ability);

        // Tap enchanted land: Attacking creatures you control get +2/-1 until end of turn. Activate this ability only if enchanted land is untapped.
        this.addAbility(new ActivateIfConditionActivatedAbility(new BoostAllEffect(
                2, -1, Duration.EndOfTurn,
                filterAttackingCreatures, false
        ), new TapAttachedCost().setText("tap enchanted land"), condition2));
    }

    private TourachsGate(final TourachsGate card) {
        super(card);
    }

    @Override
    public TourachsGate copy() {
        return new TourachsGate(this);
    }
}
