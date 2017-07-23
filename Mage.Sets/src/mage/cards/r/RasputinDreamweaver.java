/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CantHaveMoreThanAmountCountersSourceAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

/**
 * @author emerald000
 */
public class RasputinDreamweaver extends CardImpl {

    public RasputinDreamweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Rasputin Dreamweaver enters the battlefield with seven dream counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.DREAM.createInstance(7)), "seven dream counters on it"));

        // Remove a dream counter from Rasputin: Add {C} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new RemoveCountersSourceCost(CounterType.DREAM.createInstance())));

        // Remove a dream counter from Rasputin: Prevent the next 1 damage that would be dealt to Rasputin this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1), new RemoveCountersSourceCost(CounterType.DREAM.createInstance())));

        // At the beginning of your upkeep, if Rasputin started the turn untapped, put a dream counter on it.
        this.addAbility(
                new ConditionalTriggeredAbility(
                        new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.DREAM.createInstance()), TargetController.YOU, false),
                        RasputinDreamweaverStartedUntappedCondition.instance,
                        "At the beginning of your upkeep, if {this} started the turn untapped, put a dream counter on it."),
                new RasputinDreamweaverStartedUntappedWatcher());

        // Rasputin can't have more than seven dream counters on it.
        this.addAbility(new CantHaveMoreThanAmountCountersSourceAbility(CounterType.DREAM, 7));
    }

    public RasputinDreamweaver(final RasputinDreamweaver card) {
        super(card);
    }

    @Override
    public RasputinDreamweaver copy() {
        return new RasputinDreamweaver(this);
    }
}

enum RasputinDreamweaverStartedUntappedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        RasputinDreamweaverStartedUntappedWatcher watcher = (RasputinDreamweaverStartedUntappedWatcher) game.getState().getWatchers().get(RasputinDreamweaverStartedUntappedWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.startedUntapped(source.getSourceId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} started the turn untapped";
    }
}

class RasputinDreamweaverStartedUntappedWatcher extends Watcher {

    private static final FilterPermanent filter = new FilterPermanent("Untapped permanents");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    private final Set<UUID> startedUntapped = new HashSet<>(0);

    RasputinDreamweaverStartedUntappedWatcher() {
        super(RasputinDreamweaverStartedUntappedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    RasputinDreamweaverStartedUntappedWatcher(final RasputinDreamweaverStartedUntappedWatcher watcher) {
        super(watcher);
        this.startedUntapped.addAll(watcher.startedUntapped);
    }

    @Override
    public RasputinDreamweaverStartedUntappedWatcher copy() {
        return new RasputinDreamweaverStartedUntappedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.BEGINNING_PHASE_PRE) {
            game.getBattlefield().getAllActivePermanents(filter, game).stream().forEach(permanent -> startedUntapped.add(permanent.getId()));
        }
    }

    @Override
    public void reset() {
        this.startedUntapped.clear();
        super.reset();
    }

    public boolean startedUntapped(UUID cardId) {
        return this.startedUntapped.contains(cardId);
    }
}
