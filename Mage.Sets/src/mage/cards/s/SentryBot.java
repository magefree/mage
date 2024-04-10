package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.common.CreaturesAttackingYouCount;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 * @author Cguy7777
 */
public final class SentryBot extends CardImpl {

    public SentryBot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs {1} less to cast for each creature attacking you.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(CreaturesAttackingYouCount.instance)
                        .setText("this spell costs {1} less to cast for each creature attacking you"))
                .addHint(CreaturesAttackingYouCount.getHint()));

        // When Sentry Bot enters the battlefield, you get {E} for each creature attacking you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GetEnergyCountersControllerEffect(CreaturesAttackingYouCount.instance)
                        .setText("you get {E} for each creature attacking you"))
                .addHint(CreaturesAttackingYouCount.getHint()));

        // At the beginning of combat on your turn, you may pay {E}{E}{E}. If you do, put a +1/+1 counter on each creature you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersAllEffect(
                                CounterType.P1P1.createInstance(),
                                StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED),
                        new PayEnergyCost(3)),
                TargetController.YOU,
                false));
    }

    private SentryBot(final SentryBot card) {
        super(card);
    }

    @Override
    public SentryBot copy() {
        return new SentryBot(this);
    }
}
