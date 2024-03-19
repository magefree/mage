package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

/**
 * @author Cguy7777
 */
public final class OverseerOfVault76 extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public OverseerOfVault76(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First Contact -- Whenever Overseer of Vault 76 or another creature with power 3 or less
        // enters the battlefield under your control, put a quest counter on Overseer of Vault 76.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()), filter, false, true)
                .withFlavorWord("First Contact"));

        // At the beginning of combat on your turn, you may remove three quest counters from
        // among permanents you control. When you do, put a +1/+1 counter on each
        // creature you control and they gain vigilance until end of turn.
        ReflexiveTriggeredAbility boostAbility = new ReflexiveTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
                false);
        boostAbility.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE)
                .setText("and they gain vigilance until end of turn"));

        Cost cost = new RemoveCounterCost(
                new TargetPermanent(1, 3, StaticFilters.FILTER_CONTROLLED_PERMANENTS),
                CounterType.QUEST,
                3)
                .setText("remove three quest counters from among permanents you control");
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoWhenCostPaid(boostAbility, cost, "Remove three quest counters from among permanents you control?"),
                TargetController.YOU,
                false));
    }

    private OverseerOfVault76(final OverseerOfVault76 card) {
        super(card);
    }

    @Override
    public OverseerOfVault76 copy() {
        return new OverseerOfVault76(this);
    }
}
