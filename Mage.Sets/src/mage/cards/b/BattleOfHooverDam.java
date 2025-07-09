package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.ModeChoice;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class BattleOfHooverDam extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public BattleOfHooverDam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // As Battle of Hoover Dam enters the battlefield, choose NCR or Legion.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.NCR, ModeChoice.LEGION)));

        // * NCR -- At the beginning of your end step, return target creature card with mana value 3 or less
        // from your graveyard to the battlefield with a finality counter on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance())
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.NCR)));

        // * Legion -- Whenever a creature you control dies, put two +1/+1 counters on target creature you control.
        ability = new DiesCreatureTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.LEGION)));
    }

    private BattleOfHooverDam(final BattleOfHooverDam card) {
        super(card);
    }

    @Override
    public BattleOfHooverDam copy() {
        return new BattleOfHooverDam(this);
    }
}
