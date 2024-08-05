package mage.cards.t;

import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author grimreap124
 */
public final class TheRevelationsOfEzio extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterOpponentsCreaturePermanent("tapped creature an opponent controls");
    private static final FilterCard assassinFilter
            = new FilterCreatureCard("Assassin creature card from your graveyard");

    static {
        assassinFilter.add(SubType.ASSASSIN.getPredicate());
        filter.add(TappedPredicate.TAPPED);
    }

    public TheRevelationsOfEzio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);
        // I -- Destroy target tapped creature an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                ability -> {
                    ability.addEffect(new DestroyTargetEffect());
                    ability.addTarget(new TargetCreaturePermanent(filter));
                }
        );
        // II -- Whenever an Assassin you control attacks this turn, put a +1/+1 counter on it.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new CreateDelayedTriggeredAbilityEffect(new TheRevelationsOfEzioTriggeredAbility()));
        // III -- Return target Assassin creature card from your graveyard to the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                ability -> {
                    ability.addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.P1P1.createInstance(), true));
                    ability.addTarget(new TargetCardInYourGraveyard(assassinFilter));
                }
        );

        this.addAbility(sagaAbility);
    }

    private TheRevelationsOfEzio(final TheRevelationsOfEzio card) {
        super(card);
    }

    @Override
    public TheRevelationsOfEzio copy() {
        return new TheRevelationsOfEzio(this);
    }
}

class TheRevelationsOfEzioTriggeredAbility extends DelayedTriggeredAbility {

    TheRevelationsOfEzioTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.EndOfTurn, false);

    }

    private TheRevelationsOfEzioTriggeredAbility(final TheRevelationsOfEzioTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheRevelationsOfEzioTriggeredAbility copy() {
        return new TheRevelationsOfEzioTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null
                && permanent.isControlledBy(getControllerId())
                && permanent.hasSubtype(SubType.ASSASSIN, game)) {
            getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an Assassin you control attacks this turn, put a +1/+1 counter on it.";
    }
}