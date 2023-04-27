
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author nantuko
 */
public final class SteelHellkite extends CardImpl {

    public SteelHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // {2}: Steel Hellkite gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new GenericManaCost(2)
        ));
        // {X}: Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by Steel Hellkite this turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new SteelHellkiteDestroyEffect(),
                new ManaCostsImpl<>("{X}")
        ), new SteelHellkiteWatcher());

    }

    private SteelHellkite(final SteelHellkite card) {
        super(card);
    }

    @Override
    public SteelHellkite copy() {
        return new SteelHellkite(this);
    }
}

class SteelHellkiteDestroyEffect extends OneShotEffect {

    public SteelHellkiteDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with mana value X whose controller was dealt combat damage by {this} this turn";
    }

    public SteelHellkiteDestroyEffect(final SteelHellkiteDestroyEffect effect) {
        super(effect);
    }

    @Override
    public SteelHellkiteDestroyEffect copy() {
        return new SteelHellkiteDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SteelHellkiteWatcher watcher = game.getState().getWatcher(SteelHellkiteWatcher.class);
        if (watcher == null || watcher.getDamagedPlayers(source.getSourceId()).isEmpty()) {
            return false;
        }
        Set<Predicate<Permanent>> predicateSet = new HashSet<>();
        for (UUID playerId : watcher.getDamagedPlayers(source.getSourceId())) {
            predicateSet.add(new ControllerIdPredicate(playerId));
        }
        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, source.getManaCostsToPay().getX()));
        filter.add(Predicates.or(predicateSet));
        return new DestroyAllEffect(filter).apply(game, source);
    }
}

class SteelHellkiteWatcher extends Watcher {

    private final Map<UUID, Set<UUID>> damageMap = new HashMap<>();

    public SteelHellkiteWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedEvent) event).isCombatDamage()) {
            damageMap.putIfAbsent(event.getSourceId(), new HashSet<>());
            damageMap.get(event.getSourceId()).add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.damageMap.clear();
    }

    public Set<UUID> getDamagedPlayers(UUID damagerId) {
        return damageMap.getOrDefault(damagerId, new HashSet<>());
    }
}