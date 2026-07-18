package mage.cards.o;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class OriginOfThor extends CardImpl {

    public OriginOfThor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- You may discard a card. If you do, draw two cards.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new DiscardCardCost()
            )
        );

        // II -- Whenever you cast a spell this turn, put a +1/+1 counter on target creature you control.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
            new CreateDelayedTriggeredAbilityEffect(new OriginOfThorDelayedTriggeredAbility())
        );

        // III -- Target creature you control deals damage equal to its power to each opponent.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            new DamagePlayersEffect(TargetPermanentPowerCount.instance, TargetController.OPPONENT)
                .setText("Target creature you control deals damage equal to its power to each opponent"),
            new TargetControlledCreaturePermanent()
        );

        this.addAbility(sagaAbility);
    }

    private OriginOfThor(final OriginOfThor card) {
        super(card);
    }

    @Override
    public OriginOfThor copy() {
        return new OriginOfThor(this);
    }
}

class OriginOfThorDelayedTriggeredAbility extends DelayedTriggeredAbility {

    OriginOfThorDelayedTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.EndOfTurn, false, false);
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    private OriginOfThorDelayedTriggeredAbility(final OriginOfThorDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OriginOfThorDelayedTriggeredAbility copy() {
        return new OriginOfThorDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell this turn, put a +1/+1 counter on target creature you control.";
    }
}
