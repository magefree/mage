
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
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author emerald000
 */
public final class RasputinDreamweaver extends CardImpl {

    public RasputinDreamweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Rasputin Dreamweaver enters the battlefield with seven dream counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.DREAM.createInstance(7)), "seven dream counters on it"));

        // Remove a dream counter from Rasputin: Add {C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1),
                new RemoveCountersSourceCost(CounterType.DREAM.createInstance()),
                new CountersSourceCount(CounterType.DREAM)));

        // Remove a dream counter from Rasputin: Prevent the next 1 damage that would be dealt to Rasputin this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToSourceEffect(Duration.EndOfTurn, 1), new RemoveCountersSourceCost(CounterType.DREAM.createInstance())));

        // At the beginning of your upkeep, if Rasputin started the turn untapped, put a dream counter on it.
        this.addAbility(
                new ConditionalInterveningIfTriggeredAbility(
                        new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.DREAM.createInstance()), TargetController.YOU, false),
                        RasputinDreamweaverStartedUntappedCondition.instance,
                        "At the beginning of your upkeep, if {this} started the turn untapped, put a dream counter on it."),
                new RasputinDreamweaverStartedUntappedWatcher());

        // Rasputin can't have more than seven dream counters on it.
        this.addAbility(new CantHaveMoreThanAmountCountersSourceAbility(CounterType.DREAM, 7));
    }

    private RasputinDreamweaver(final RasputinDreamweaver card) {
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
        RasputinDreamweaverStartedUntappedWatcher watcher = game.getState().getWatcher(RasputinDreamweaverStartedUntappedWatcher.class);
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
        filter.add(TappedPredicate.UNTAPPED);
    }

    private final Set<UUID> startedUntapped = new HashSet<>(0);

    RasputinDreamweaverStartedUntappedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE) {
            game.getBattlefield().getAllActivePermanents(filter, game).forEach(permanent -> startedUntapped.add(permanent.getId()));
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
