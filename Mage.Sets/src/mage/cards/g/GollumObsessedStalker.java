package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class GollumObsessedStalker extends CardImpl {

    public GollumObsessedStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Skulk
        this.addAbility(new SkulkAbility());

        // At the beginning of your end step, each opponent dealt combat damage this game by a creature named Gollum, Obsessed Stalker loses life equal to the amount of life you gained this turn.
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(
                new GollumObsessedStalkerEffect(),
                false
        );
        ability.addWatcher(new PlayerGainedLifeWatcher());
        ability.addWatcher(new GollumObsessedStalkerWatcher());
        ability.addHint(ControllerGainedLifeCount.getHint());
        ability.addHint(GollumObsessedStalkerHint.instance);

        this.addAbility(ability);
    }

    private GollumObsessedStalker(final GollumObsessedStalker card) {
        super(card);
    }

    @Override
    public GollumObsessedStalker copy() {
        return new GollumObsessedStalker(this);
    }
}

class GollumObsessedStalkerWatcher extends Watcher {

    // For each creature name, the players damaged by them during combat.
    private final Map<String, Set<UUID>> playersPerName = new HashMap<>();

    public GollumObsessedStalkerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PLAYER
                || !((DamagedPlayerEvent) event).isCombatDamage()) {
            return;
        }
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature == null) {
            return;
        }

        String name = creature.getName();
        UUID playerId = event.getPlayerId();
        if (creature.getName().isEmpty() || playerId == null) {
            return;
        }

        playersPerName.computeIfAbsent(name, k -> new HashSet());
        playersPerName.get(name).add(playerId);
    }

    public Set<UUID> getPlayersDamagedByNamed(String name) {
        return playersPerName.getOrDefault(name, new HashSet<>());
    }
}

class GollumObsessedStalkerEffect extends OneShotEffect {

    GollumObsessedStalkerEffect() {
        super(Outcome.LoseLife);
        staticText = "each opponent dealt combat damage this game by a creature named "
                + "{this} loses life equal to the amount of life you gained this turn.";
    }

    private GollumObsessedStalkerEffect(final GollumObsessedStalkerEffect effect) {
        super(effect);
    }

    @Override
    public GollumObsessedStalkerEffect copy() {
        return new GollumObsessedStalkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GollumObsessedStalkerWatcher damageWatcher = game.getState().getWatcher(GollumObsessedStalkerWatcher.class);
        PlayerGainedLifeWatcher lifeWatcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        Permanent gollum = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (damageWatcher == null || lifeWatcher == null || gollum == null) {
            return false;
        }

        String name = gollum.getName();
        int amount = lifeWatcher.getLifeGained(source.getControllerId());
        Set<UUID> playersDamaged = damageWatcher.getPlayersDamagedByNamed(name);

        if (amount == 0 || playersDamaged.isEmpty()) {
            return true;
        }

        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            if (!playersDamaged.contains(playerId)) {
                continue;
            }

            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            player.loseLife(amount, game, source, false);
        }

        return true;
    }
}

enum GollumObsessedStalkerHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        GollumObsessedStalkerWatcher watcher = game.getState().getWatcher(GollumObsessedStalkerWatcher.class);
        if (watcher == null) {
            return "";
        }

        String name = null;
        Permanent gollum = game.getPermanentOrLKIBattlefield(ability.getSourceId());
        if (gollum != null) {
            // Gollum is or was in play, its name is using LKI.
            name = gollum.getName();
        } else {
            // if Gollum LKI not in play (like in hand or in command zone),
            // find the object.
            MageObject gollumObj = game.getObject(ability.getSourceId());
            if (gollumObj != null) {
                name = gollumObj.getName();
            }
        }
        if (name.isEmpty()) {
            return "";
        }

        // Not filtering by opponent intentionally, just to provide full info everywhere.
        List<String> namesOfPlayersDealtDamage =
                watcher.getPlayersDamagedByNamed(name)
                       .stream()
                       .map(game::getPlayer)
                       .filter(Objects::nonNull)
                       .map(Player::getName)
                       .filter(n -> !n.isEmpty())
                       .collect(Collectors.toList());

        return "Players dealt combat damage by creatures named " + name + " this game: ["
                + String.join(", ", namesOfPlayersDealtDamage)
                + "]";
    }

    @Override
    public GollumObsessedStalkerHint copy() {
        return instance;
    }
}