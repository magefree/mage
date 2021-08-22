package mage.cards.a;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class AngelOfDestiny extends CardImpl {

    public AngelOfDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, you and that player each gain that much life.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AngelOfDestinyGainLifeEffect(), StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true, true
        ));

        // At the beginning of your end step, if you have at least 15 life more than your starting life total, each player Angel of Destiny attacked this turn loses the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new AngelOfDestinyLoseEffect(), TargetController.YOU, false
                ), AngelOfDestinyCondition.instance, "At the beginning of your end step, " +
                "if you have at least 15 life more than your starting life total, " +
                "each player {this} attacked this turn loses the game."
        ), new AngelOfDestinyWatcher());
    }

    private AngelOfDestiny(final AngelOfDestiny card) {
        super(card);
    }

    @Override
    public AngelOfDestiny copy() {
        return new AngelOfDestiny(this);
    }
}

enum AngelOfDestinyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() >= game.getStartingLife() + 15;
    }
}

class AngelOfDestinyGainLifeEffect extends OneShotEffect {

    AngelOfDestinyGainLifeEffect() {
        super(Outcome.Benefit);
        staticText = "you and that player each gain that much life";
    }

    private AngelOfDestinyGainLifeEffect(final AngelOfDestinyGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfDestinyGainLifeEffect copy() {
        return new AngelOfDestinyGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        int damage = (int) getValue("damage");
        if (controller != null) {
            controller.gainLife(damage, game, source);
        }
        if (player != null) {
            player.gainLife(damage, game, source);
        }
        return true;
    }
}

class AngelOfDestinyLoseEffect extends OneShotEffect {

    AngelOfDestinyLoseEffect() {
        super(Outcome.Benefit);
    }

    private AngelOfDestinyLoseEffect(final AngelOfDestinyLoseEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfDestinyLoseEffect copy() {
        return new AngelOfDestinyLoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AngelOfDestinyWatcher watcher = game.getState().getWatcher(AngelOfDestinyWatcher.class);
        if (watcher == null) {
            return false;
        }
        Set<UUID> playerSet = watcher.getPlayers(new MageObjectReference(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter(), game
        ));
        if (playerSet == null) {
            return false;
        }
        for (UUID playerId : playerSet) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.lost(game);
        }
        return true;
    }
}

class AngelOfDestinyWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    AngelOfDestinyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        map.computeIfAbsent(
                new MageObjectReference(event.getSourceId(), game), u -> new HashSet<>()
        ).add(event.getTargetId());
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    Set<UUID> getPlayers(MageObjectReference mor) {
        return map.getOrDefault(mor, null);
    }
}
