package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DiesOneOrMoreCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class BloodSpatterAnalysis extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.BLOODSTAIN, 5);

    public BloodSpatterAnalysis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{R}");

        // When Blood Spatter Analysis enters the battlefield, it deals 3 damage to target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more creatures die, mill a card and put a bloodstain counter on Blood Spatter Analysis.
        // Then sacrifice it if it has five or more bloodstain counters on it.
        // When you do, return target creature card from your graveyard to your hand.
        ability = new DiesOneOrMoreCreaturesTriggeredAbility(new MillCardsControllerEffect(1));
        ability.addEffect(new AddCountersSourceEffect(CounterType.BLOODSTAIN.createInstance()).concatBy("and"));
        ReflexiveTriggeredAbility returnAbility = new ReflexiveTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        returnAbility.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addEffect(new ConditionalOneShotEffect(new DoWhenCostPaid(
                returnAbility, new SacrificeSourceCost(), null, false
        ), condition).setText("Then sacrifice it if it has five or more bloodstain counters on it. " +
                "When you do, return target creature card from your graveyard to your hand"));
        this.addAbility(ability);
    }

    private BloodSpatterAnalysis(final BloodSpatterAnalysis card) {
        super(card);
    }

    @Override
    public BloodSpatterAnalysis copy() {
        return new BloodSpatterAnalysis(this);
    }
}
