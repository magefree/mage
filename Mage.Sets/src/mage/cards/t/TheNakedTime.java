package mage.cards.t;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBasicCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheNakedTime extends CardImpl {

    public TheNakedTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Search your library for a basic Mountain card, put it onto the battlefield tapped, then shuffle.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new SearchLibraryPutInPlayEffect(
            new TargetCardInLibrary(new FilterBasicCard(SubType.MOUNTAIN)), true
        ));

        // II -- Whenever a creature you control enters this turn, put a +1/+1 counter on it and it gains haste until end of turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II,
            new CreateDelayedTriggeredAbilityEffect(new TheNakedTimeTriggeredAbility()));

        // III -- Sacrifice a land.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III,
            new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 1, ""));
        this.addAbility(sagaAbility);
    }

    private TheNakedTime(final TheNakedTime card) {
        super(card);
    }

    @Override
    public TheNakedTime copy() {
        return new TheNakedTime(this);
    }
}

class TheNakedTimeTriggeredAbility extends DelayedTriggeredAbility {

    TheNakedTimeTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
            .setText("put a +1/+1 counter on it"), Duration.EndOfTurn, false);
        this.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
            .setText("and it gains haste until end of turn"));
        this.setTriggerPhrase("Whenever a creature you control enters this turn, ");
    }

    private TheNakedTimeTriggeredAbility(final TheNakedTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheNakedTimeTriggeredAbility copy() {
        return new TheNakedTimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game) || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}
