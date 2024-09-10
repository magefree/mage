package org.mage.test.cards.triggers;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

public class CounterRemovalTests extends CardTestCommander4Players {

    @Test
    public void CounterRemovalTest(){

        int nCountersToAdd = 3;

        addCard(Zone.HAND, playerA, "Basri's Solidarity", nCountersToAdd);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        // Remove all counters from all permanents and exile all tokens.
        addCard(Zone.HAND, playerA, "Aether Snap");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        Ability ability = new MultiCountersRemovedTriggeredAbility();
        addCustomCardWithAbility("multicountertrigdcard", playerA, ability, null, CardType.CREATURE, "", Zone.BATTLEFIELD);

        ability = new SingleCounterRemovedTriggeredAbility();
        addCustomCardWithAbility("singlecountertrigdcard", playerA, ability, null, CardType.CREATURE, "", Zone.BATTLEFIELD);

        ability = new SimpleStaticAbility(new MultiCountersRemoveReplacementEffect());
        addCustomCardWithAbility("multicounterreplcard", playerA, ability, null, CardType.CREATURE, "", Zone.BATTLEFIELD);

        ability = new SimpleStaticAbility(new SingleCounterRemoveReplacementEffect());
        addCustomCardWithAbility("singlecounterreplcard", playerA, ability, null, CardType.CREATURE, "", Zone.BATTLEFIELD);

        for (int i = 0; i < nCountersToAdd; ++i){
            castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Basri's Solidarity", true);
        }

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aether Snap", true);

        //choose the <nCountersToAdd> single counter triggers to go on the stack first
        setChoice(playerA, "When a counter", nCountersToAdd);

        setStrictChooseMode(true);
        execute();

        assertLife(playerA, currentGame.getStartingLife() - 1);
        assertCounterCount(playerA, CounterType.ENERGY, nCountersToAdd);
        assertCounterCount("multicounterreplcard", CounterType.P1P1, 1);
        assertCounterCount("singlecounterreplcard", CounterType.P1P1, 2);

    }

}

class SingleCounterRemoveReplacementEffect extends ReplacementEffectImpl {

    SingleCounterRemoveReplacementEffect() {
        super(Duration.Custom, Outcome.Benefit);
        this.setText("When a counter would be removed from {this} that would bring it to less than 2 of that counter, that counter is not removed.");
    }

    private SingleCounterRemoveReplacementEffect(final SingleCounterRemoveReplacementEffect ability) {
        super(ability);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                int countersCount = sourcePermanent.getCounters(game).getCount(event.getData());
                return countersCount - 1 < 2;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REMOVE_COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public SingleCounterRemoveReplacementEffect copy() {
        return new SingleCounterRemoveReplacementEffect(this);
    }

}

class MultiCountersRemoveReplacementEffect extends ReplacementEffectImpl {

    MultiCountersRemoveReplacementEffect() {
        super(Duration.Custom, Outcome.Benefit);
        this.setText("When any number of counters would be removed from {this}, one less counter is removed.");
    }

    private MultiCountersRemoveReplacementEffect(final MultiCountersRemoveReplacementEffect ability) {
        super(ability);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REMOVE_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            event.setAmount(event.getAmount() - 1);
        }
        return false;
    }

    @Override
    public MultiCountersRemoveReplacementEffect copy() {
        return new MultiCountersRemoveReplacementEffect(this);
    }

}

class MultiCountersRemovedTriggeredAbility extends TriggeredAbilityImpl {

    MultiCountersRemovedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeSourceControllerEffect(1));
        this.setTriggerPhrase("When any number of counters are removed from {this}, ");
    }

    private MultiCountersRemovedTriggeredAbility(final MultiCountersRemovedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MultiCountersRemovedTriggeredAbility copy() {
        return new MultiCountersRemovedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }

}

class SingleCounterRemovedTriggeredAbility extends TriggeredAbilityImpl {

    SingleCounterRemovedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersPlayersEffect(CounterType.ENERGY.createInstance(), TargetController.YOU));
        this.setTriggerPhrase("When a counter is removed from {this}, ");
    }

    private SingleCounterRemovedTriggeredAbility(final SingleCounterRemovedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SingleCounterRemovedTriggeredAbility copy() {
        return new SingleCounterRemovedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }

}
