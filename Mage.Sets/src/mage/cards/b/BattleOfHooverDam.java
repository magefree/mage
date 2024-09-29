package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class BattleOfHooverDam extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BattleOfHooverDam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // As Battle of Hoover Dam enters the battlefield, choose NCR or Legion.
        this.addAbility(new AsEntersBattlefieldAbility(
                new ChooseModeEffect("NCR or Legion?", "NCR", "Legion")));

        // * NCR -- At the beginning of your end step, return target creature card with mana value 3 or less
        // from your graveyard to the battlefield with a finality counter on it.
        Ability ncrAbility = new ConditionalTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()),
                        TargetController.YOU,
                        false),
                new ModeChoiceSourceCondition("NCR"),
                "&bull  NCR &mdash; At the beginning of your end step, return target creature card with " +
                        "mana value 3 or less from your graveyard to the battlefield with a finality counter on it.");
        ncrAbility.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ncrAbility);

        // * Legion -- Whenever a creature you control dies, put two +1/+1 counters on target creature you control.
        Ability legionAbility = new ConditionalTriggeredAbility(
                new DiesCreatureTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                        false,
                        StaticFilters.FILTER_CONTROLLED_A_CREATURE),
                new ModeChoiceSourceCondition("Legion"),
                "&bull  Legion &mdash; Whenever a creature you control dies, " +
                        "put two +1/+1 counters on target creature you control.");
        legionAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(legionAbility);
    }

    private BattleOfHooverDam(final BattleOfHooverDam card) {
        super(card);
    }

    @Override
    public BattleOfHooverDam copy() {
        return new BattleOfHooverDam(this);
    }
}
