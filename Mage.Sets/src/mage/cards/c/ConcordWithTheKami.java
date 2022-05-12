package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.permanent.token.SpiritToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConcordWithTheKami extends CardImpl {

    private static final FilterPermanent counterFilter
            = new FilterCreaturePermanent("creature with a counter on it");
    private static final FilterPermanent enchantedFilter
            = new FilterControlledCreaturePermanent();
    private static final FilterPermanent equippedFilter
            = new FilterControlledCreaturePermanent();

    static {
        counterFilter.add(CounterAnyPredicate.instance);
        enchantedFilter.add(EnchantedPredicate.instance);
        equippedFilter.add(EquippedPredicate.instance);
    }

    private static final Condition enchantedCondition
            = new PermanentsOnTheBattlefieldCondition(enchantedFilter);
    private static final Condition equippedCondition
            = new PermanentsOnTheBattlefieldCondition(equippedFilter);
    private static final Hint enchantedHint
            = new ConditionHint(enchantedCondition, "You control an enchanted creature");
    private static final Hint equippedHint
            = new ConditionHint(equippedCondition, "You control an equipped creature");

    public ConcordWithTheKami(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // At the beginning of your end step, choose one or more —
        // • Put a +1/+1 counter on target creature with a counter on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(counterFilter));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);

        // • Draw a card if you control an enchanted creature.
        ability.addMode(new Mode(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), enchantedCondition,
                "draw a card if you control an enchanted creature"
        )));

        // • Create a 1/1 colorless Spirit creature token if you control an equipped creature.
        ability.addMode(new Mode(new ConditionalOneShotEffect(
                new CreateTokenEffect(new SpiritToken()), equippedCondition,
                "create a 1/1 colorless Spirit creature token if you control an equipped creature"
        )));
        this.addAbility(ability.addHint(enchantedHint).addHint(equippedHint));
    }

    private ConcordWithTheKami(final ConcordWithTheKami card) {
        super(card);
    }

    @Override
    public ConcordWithTheKami copy() {
        return new ConcordWithTheKami(this);
    }
}
