package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class SilumgarScavenger extends CardImpl {

    public SilumgarScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Exploit (When this creature enters the battlefield, you may sacrifice a creature.)
        this.addAbility(new ExploitAbility());

        // Whenever another creature you control dies, put a +1/+1 counter on Silumgar Scavenger. It gains haste until end of turn if it exploited that creature.
        Ability ability = new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true);
        ability.addEffect(new SilumgarScavengerBoostEffect());
        //ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability, new SilumgarScavengerExploitedWatcher());

    }

    private SilumgarScavenger(final SilumgarScavenger card) {
        super(card);
    }

    @Override
    public SilumgarScavenger copy() {
        return new SilumgarScavenger(this);
    }
}

class SilumgarScavengerExploitedWatcher extends Watcher {

    private final Map<UUID, Integer> exploitedPermanents = new HashMap<>(); // id + zone

    SilumgarScavengerExploitedWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.EXPLOITED_CREATURE) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null) {
                this.exploitedPermanents.put(permanent.getId(), permanent.getZoneChangeCounter(game));
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.exploitedPermanents.clear();
    }

    boolean isPermanentExploited(Game game, UUID permanentId) {
        Permanent currentPerm = game.getPermanentOrLKIBattlefield(permanentId);
        if (currentPerm != null && this.exploitedPermanents.containsKey(currentPerm.getId())) {
            int currentZone = currentPerm.getZoneChangeCounter(game);
            return currentZone == this.exploitedPermanents.get(currentPerm.getId());
        }
        return false;
    }
}

class SilumgarScavengerBoostEffect extends OneShotEffect {

    public SilumgarScavengerBoostEffect() {
        super(Outcome.AddAbility);
        staticText = "It gains haste until end of turn if it exploited that creature";
    }

    public SilumgarScavengerBoostEffect(SilumgarScavengerBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID diesPermanent = this.getTargetPointer().getFirst(game, source);
        SilumgarScavengerExploitedWatcher watcher = game.getState().getWatcher(SilumgarScavengerExploitedWatcher.class);
        if (watcher != null && watcher.isPermanentExploited(game, diesPermanent)) {
            ContinuousEffect effect = new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public SilumgarScavengerBoostEffect copy() {
        return new SilumgarScavengerBoostEffect(this);
    }
}
