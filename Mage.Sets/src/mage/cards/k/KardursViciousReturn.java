package mage.cards.k;

import java.util.UUID;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class KardursViciousReturn extends CardImpl {

    public KardursViciousReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — You may sacrifice a creature. When you do, Kardur's Vicious Return deals 3 damage to any target.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(3), false,
                "{this} deals 3 damage to any target"
        );
        ability.addTarget(new TargetAnyTarget());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new DoWhenCostPaid(
                ability, new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                "Sacrifice a creature?"
        ));

        // II — Each player discards a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new DiscardEachPlayerEffect());

        // III — Return target creature card from your graveyard to the battlefield.
        // Put a +1/+1 counter on it. It gains haste until your next turn.
        Effects effects = new Effects(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("Put a +1/+1 counter on it"),
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.UntilYourNextTurn)
                        .setText("It gains haste until your next turn")
        );
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III, effects,
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );
        this.addAbility(sagaAbility);
    }

    private KardursViciousReturn(final KardursViciousReturn card) {
        super(card);
    }

    @Override
    public KardursViciousReturn copy() {
        return new KardursViciousReturn(this);
    }
}
