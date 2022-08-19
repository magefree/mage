package mage.cards.s;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.*;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class ShareTheSpoils extends CardImpl {

    public ShareTheSpoils(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // When Share the Spoils enters the battlefield or an opponent loses the game,
        // exile the top card of each player’s library.exile the top card of each player’s library.
        this.addAbility(new ShareTheSpoilsExileETBAndPlayerLossAbility());

        // During each player’s turn, that player may play a land or cast a spell from among cards exiled with Share the Spoils,
        // and they may spend mana as though it were mana of any color to cast that spell.
        Ability castAbility = new SimpleStaticAbility(new ShareTheSpoilsPlayExiledCardEffect());
        castAbility.setIdentifier(MageIdentifier.ShareTheSpoilsWatcher);
        this.addAbility(castAbility, new ShareTheSpoilsWatcher());

        // When they do, exile the top card of their library.
        Ability exileCardWhenPlayedACard = new ShareTheSpoilsExileCardWhenPlayACardAbility();
        this.addAbility(exileCardWhenPlayedACard);
    }

    private ShareTheSpoils(final ShareTheSpoils card) {
        super(card);
    }

    @Override
    public ShareTheSpoils copy() {
        return new ShareTheSpoils(this);
    }
}

//-- Exile from Everyone --//
class ShareTheSpoilsExileETBAndPlayerLossAbility extends TriggeredAbilityImpl {

    ShareTheSpoilsExileETBAndPlayerLossAbility() {
        super(Zone.BATTLEFIELD, new ShareTheSpoilsExileCardFromEveryoneEffect());
    }

    private ShareTheSpoilsExileETBAndPlayerLossAbility(final ShareTheSpoilsExileETBAndPlayerLossAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD ||
                event.getType() == GameEvent.EventType.LOST || // Player conceedes
                event.getType() == GameEvent.EventType.LOSES;  // Player loses by all other means
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // The card that entered the battlefield was this one
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(getSourceId());
        }

        // A player lost the game
        return true;
    }

    @Override
    public TriggeredAbility copy() {
        return new ShareTheSpoilsExileETBAndPlayerLossAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or an opponent loses the game, " +
                "exile the top card of each player's library.";
    }
}

class ShareTheSpoilsExileCardFromEveryoneEffect extends OneShotEffect {

    public ShareTheSpoilsExileCardFromEveryoneEffect() {
        super(Outcome.Exile);
    }

    public ShareTheSpoilsExileCardFromEveryoneEffect(final ShareTheSpoilsExileCardFromEveryoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source == null) {
            return false;
        }

        PlayerList players = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID playerId : players) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            Card topLibraryCard = player.getLibrary().getFromTop(game);
            if (topLibraryCard == null) {
                continue;
            }

            boolean moved = player.moveCardsToExile(
                    topLibraryCard,
                    source,
                    game,
                    true,
                    CardUtil.getExileZoneId(game, source),
                    CardUtil.getSourceName(game, source)
            );

            if (moved) {
                ShareTheSpoilsSpendAnyManaEffect effect = new ShareTheSpoilsSpendAnyManaEffect();
                effect.setTargetPointer(new FixedTarget(topLibraryCard, game));
                game.addEffect(effect, source);
            }
        }
        return true;
    }

    @Override
    public ShareTheSpoilsExileCardFromEveryoneEffect copy() {
        return new ShareTheSpoilsExileCardFromEveryoneEffect(this);
    }
}

//-- Play a card Exiled by Share the Spoils --//
class ShareTheSpoilsPlayExiledCardEffect extends AsThoughEffectImpl {

    ShareTheSpoilsPlayExiledCardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PutCardInPlay);
        staticText = "During each player's turn, " +
                "that player may play a land or cast a spell from among cards exiled with {this}, " +
                "and they may spend mana as though it were mana of any color to cast that spell. " +
                "When they do, exile the top card of their library.";
    }

    private ShareTheSpoilsPlayExiledCardEffect(final ShareTheSpoilsPlayExiledCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        // Have to play on your turn
        if (!game.getActivePlayerId().equals(affectedControllerId)) {
            return false;
        }

        // Not in exile
        if (game.getState().getZone(CardUtil.getMainCardId(game, sourceId)) != Zone.EXILED) {
            return false;
        }

        // TODO: This is a workaround for #8706, remove when that's fixed.
        int zoneChangeCounter = game.getState().getZoneChangeCounter(source.getSourceId());
        // Not a card exiled with this Share the Spoils
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
        if (exileZone == null) {
            return false;
        }
        if (!exileZone.contains(CardUtil.getMainCardId(game, sourceId))) {
            return false;
        }

        ShareTheSpoilsWatcher watcher = game.getState().getWatcher(ShareTheSpoilsWatcher.class);
        if (watcher == null) {
            return false;
        }

        return watcher.hasNotUsedAbilityThisTurn();
    }

    @Override
    public ShareTheSpoilsPlayExiledCardEffect copy() {
        return new ShareTheSpoilsPlayExiledCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

//-- Spend mana as any color --//
class ShareTheSpoilsSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    ShareTheSpoilsSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    private ShareTheSpoilsSpendAnyManaEffect(final ShareTheSpoilsSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ShareTheSpoilsSpendAnyManaEffect copy() {
        return new ShareTheSpoilsSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID mainCardId = CardUtil.getMainCardId(game, sourceId);

        if (getTargetPointer() == null) {
            return false;
        }
        UUID targetUUID = ((FixedTarget) getTargetPointer()).getTarget();

        // Not the right card
        if (mainCardId != targetUUID) {
            return false;
        }

        int mainCardZCC = game.getState().getZoneChangeCounter(mainCardId);
        int targetZCC = game.getState().getZoneChangeCounter(targetUUID);

        if (mainCardZCC <= targetZCC + 1) {
            // card moved one zone, from exile to being cast
            return true;
        } else {
            // card moved zones, effect can be discarded
            this.discard();
            return false;
        }
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

//-- Exile another card when a card is played that was exiled with Share the Spoils  --//
class ShareTheSpoilsExileCardWhenPlayACardAbility extends TriggeredAbilityImpl {

    private UUID triggeringPlayerID;

    ShareTheSpoilsExileCardWhenPlayACardAbility() {
        super(Zone.BATTLEFIELD, new ShareTheSpoilsExileSingleCardEffect());
        setRuleVisible(false);
        setTriggerPhrase("When they do");
    }

    private ShareTheSpoilsExileCardWhenPlayACardAbility(final ShareTheSpoilsExileCardWhenPlayACardAbility ability) {
        super(ability);
        triggeringPlayerID = ability.triggeringPlayerID;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.hasApprovingIdentifier(MageIdentifier.ShareTheSpoilsWatcher);
    }

    @Override
    public TriggeredAbility copy() {
        return new ShareTheSpoilsExileCardWhenPlayACardAbility(this);
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        // Keep track of who triggered this ability, so the effect can know later.
        // Do this before the ability is copied in super.trigger()
        triggeringPlayerID = triggeringEvent.getPlayerId();

        super.trigger(game, controllerId, triggeringEvent);
    }

    public UUID getTriggeringPlayerID() {
        return triggeringPlayerID;
    }
}

class ShareTheSpoilsExileSingleCardEffect extends OneShotEffect {

    ShareTheSpoilsExileSingleCardEffect() {
        super(Outcome.Exile);
        staticText = ", exile the top card of their library";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source == null) {
            return false;
        }

        Player player = game.getPlayer(((ShareTheSpoilsExileCardWhenPlayACardAbility) source).getTriggeringPlayerID());
        if (player == null) {
            return false;
        }

        Card topLibraryCard = player.getLibrary().getFromTop(game);
        if (topLibraryCard == null) {
            return false;
        }

        boolean moved = player.moveCardsToExile(
                topLibraryCard,
                source,
                game,
                true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );

        if (moved) {
            ShareTheSpoilsSpendAnyManaEffect effect = new ShareTheSpoilsSpendAnyManaEffect();
            effect.setTargetPointer(new FixedTarget(topLibraryCard, game));
            game.addEffect(effect, source);
        }
        return moved;
    }

    private ShareTheSpoilsExileSingleCardEffect(final ShareTheSpoilsExileSingleCardEffect effect) {
        super(effect);
    }

    @Override
    public ShareTheSpoilsExileSingleCardEffect copy() {
        return new ShareTheSpoilsExileSingleCardEffect(this);
    }
}

class ShareTheSpoilsWatcher extends Watcher {

    private boolean usedThisTurn;

    public ShareTheSpoilsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST && event.getType() != GameEvent.EventType.LAND_PLAYED) {
            return;
        }

        if (event.hasApprovingIdentifier(MageIdentifier.ShareTheSpoilsWatcher)) {
            usedThisTurn = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedThisTurn = false;
    }

    public boolean hasNotUsedAbilityThisTurn() {
        return !usedThisTurn;
    }
}
