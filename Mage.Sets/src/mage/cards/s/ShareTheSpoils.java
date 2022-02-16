package mage.cards.s;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.*;
import mage.cards.*;
import mage.constants.*;
import mage.game.CardState;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.players.PlayerList;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public final class ShareTheSpoils extends CardImpl {

    // Number used to ensure that each cast of Share the Spoils has its own unique exile pile
    //      "Once Share the Spoils leaves the battlefield, players may no longer play the exiled cards.
    //       If Share the Spoils is destroyed and somehow returns to the battlefield,
    //       it’s a new object with no memory of the previously exiled cards."
    private final String exileZoneIdString;

    public ShareTheSpoils(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Increment everytime a new copy is cast so that they don't share
        exileZoneIdString = this.getId().toString();

        // When Share the Spoils enters the battlefield or an opponent loses the game,
        // exile the top card of each player’s library.exile the top card of each player’s library.
        this.addAbility(new ShareTheSpoilsExileETBAndPlayerLossAbility(exileZoneIdString));

        // During each player’s turn, that player may play a land or cast a spell from among cards exiled with Share the Spoils,
        Ability castAbility = new SimpleStaticAbility(new ShareTheSpoilsPlayExiledCardEffect(exileZoneIdString));
        // and they may spend mana as though it were mana of any color to cast that spell.
        castAbility.addEffect(new ShareTheSpoilsSpendAnyManaEffect(exileZoneIdString));
        castAbility.setIdentifier(MageIdentifier.ShareTheSpoilsWatcher);
        this.addAbility(castAbility, new ShareTheSpoilsWatcher());

        // When they do, exile the top card of their library.
        Ability exileCardWhenPlayedACard = new ShareTheSpoilsExileCardWhenPlayACardAbility(exileZoneIdString);
        this.addAbility(exileCardWhenPlayedACard);
    }

    private ShareTheSpoils(final ShareTheSpoils card) {
        super(card);
        this.exileZoneIdString = card.exileZoneIdString;
    }

    @Override
    public ShareTheSpoils copy() { return new ShareTheSpoils(this); }
}

//-- Exile from Everyone --//
class ShareTheSpoilsExileETBAndPlayerLossAbility extends TriggeredAbilityImpl {

    ShareTheSpoilsExileETBAndPlayerLossAbility(String exileZoneIdString) {
        super(Zone.BATTLEFIELD, new ShareTheSpoilsExileCardFromEveryoneEffect(exileZoneIdString));
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
    public String getTriggerPhrase() {
        return "When Share the Spoils enters the battlefield or an opponent loses the game";
    }
}

class ShareTheSpoilsExileCardFromEveryoneEffect extends OneShotEffect {

    private final String exileZoneIdString;

    public ShareTheSpoilsExileCardFromEveryoneEffect(String exileZoneIdString) {
        super(Outcome.Exile);
        staticText = ", exile the top card of each player's library.";
        this.exileZoneIdString = exileZoneIdString;
    }

    public ShareTheSpoilsExileCardFromEveryoneEffect(final ShareTheSpoilsExileCardFromEveryoneEffect effect) {
        super(effect);
        this.exileZoneIdString = effect.exileZoneIdString;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source == null) { return false; }

        // Create an exile zone unique to this card
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneIdString, game));

        if (exileZone == null) {
            exileZone = game.getExile().createZone(
                    CardUtil.getExileZoneId(exileZoneIdString, game),
                    "Share the Spoils");
        }

        PlayerList players = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID playerId : players) {
            Player player = game.getPlayer(playerId);

            if (player == null) { continue; }

            Card topLibraryCard = player.getLibrary().getFromTop(game);

            if (topLibraryCard == null) { continue; }

            player.moveCardsToExile(topLibraryCard, source, game, true, exileZone.getId(), exileZone.getName());
        }
        return true;
    }

    @Override
    public ShareTheSpoilsExileCardFromEveryoneEffect copy() { return new ShareTheSpoilsExileCardFromEveryoneEffect(this); }
}

//-- Play a card Exiled by Share the Spoils --//
class ShareTheSpoilsPlayExiledCardEffect extends AsThoughEffectImpl {

    private final String exileZoneIdString;

    ShareTheSpoilsPlayExiledCardEffect(String exileZoneIdString) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PutCardInPlay);
        staticText = "During each player's turn, " +
                "that player may play a land or cast a spell from among cards exiled with Share the Spoils, " +
                "and they may spend mana as though it were mana of any color to cast that spell";
        this.exileZoneIdString = exileZoneIdString;
    }

    private ShareTheSpoilsPlayExiledCardEffect(final ShareTheSpoilsPlayExiledCardEffect effect) {
        super(effect);
        this.exileZoneIdString = effect.exileZoneIdString;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(sourceId);
        if (card == null) { return false; }

        // Have to play on your turn
        if (!game.getActivePlayerId().equals(affectedControllerId)) { return false; }

        // Not in exile
        if (game.getState().getZone(card.getMainCard().getId()) != Zone.EXILED) { return false; }

        // Not a card exiled with this Share the Spoils
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneIdString, game));
        if (exileZone == null) { return false; }
        if (!exileZone.contains(sourceId)) { return false; }

        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null) { return false; }

        ShareTheSpoilsWatcher watcher = game.getState().getWatcher(ShareTheSpoilsWatcher.class);

        return watcher.hasNotUsedAbilityThisTurn(new MageObjectReference(sourceObject, game));
    }

    @Override
    public ShareTheSpoilsPlayExiledCardEffect copy() { return new ShareTheSpoilsPlayExiledCardEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) { return true; }
}

//-- Spend mana as any color --//
class ShareTheSpoilsSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final String exileZoneIdString;

    ShareTheSpoilsSpendAnyManaEffect(String exileZoneIdString) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.exileZoneIdString = exileZoneIdString;
    }

    private ShareTheSpoilsSpendAnyManaEffect(final ShareTheSpoilsSpendAnyManaEffect effect) {
        super(effect);
        this.exileZoneIdString = effect.exileZoneIdString;
    }

    @Override
    public boolean apply(Game game, Ability source) { return true; }

    @Override
    public ShareTheSpoilsSpendAnyManaEffect copy() { return new ShareTheSpoilsSpendAnyManaEffect(this); }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(sourceId);
        if (card == null) { return false; }

        // Check Exile
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneIdString, game));
        if (exileZone == null) { return false; }
        if (exileZone.contains(sourceId)) { return true; }

        // Check Stack
        UUID cardID;
        Card mainCard = card.getMainCard();
        if (mainCard instanceof ModalDoubleFacesCard) {
            cardID = ((ModalDoubleFacesCard) mainCard).getLeftHalfCard().getId();
        } else {
            cardID = mainCard.getId();
        }

        CardState cardState = game.getLastKnownInformationCard(cardID, Zone.EXILED);

        // TODO: Currently works for all cards from exile
        return cardState != null;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}


//-- Exile when a card is played that was exiled with Share the Spoil's  --//
class ShareTheSpoilsExileCardWhenPlayACardAbility extends TriggeredAbilityImpl {

    ShareTheSpoilsExileCardWhenPlayACardAbility(String exileZoneIdString) {
        super(Zone.BATTLEFIELD, new ShareTheSpoilsExileSingleCardEffect(exileZoneIdString));
    }

    private ShareTheSpoilsExileCardWhenPlayACardAbility(final ShareTheSpoilsExileCardWhenPlayACardAbility ability) {
        super(ability);
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
    public TriggeredAbility copy() { return new ShareTheSpoilsExileCardWhenPlayACardAbility(this); }

    @Override
    public String getTriggerPhrase() { return "When they do"; }
}

class ShareTheSpoilsExileSingleCardEffect extends OneShotEffect {

    private final String exileZoneIdString;

    ShareTheSpoilsExileSingleCardEffect(String exileZoneIdString) {
        super(Outcome.Exile);
        staticText = ", exile the top card of their library";
        this.exileZoneIdString = exileZoneIdString;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source == null) { return false; }

        // Not a card exiled with this Share the Spoils
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneIdString, game));
        if (exileZone == null) { return false; }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) { return false; }

        Card topLibraryCard = player.getLibrary().getFromTop(game);
        if (topLibraryCard == null) { return false; }

        player.moveCardsToExile(topLibraryCard, source, game, true, exileZone.getId(), exileZone.getName());

        return true;
    }

    private ShareTheSpoilsExileSingleCardEffect(final ShareTheSpoilsExileSingleCardEffect effect) {
        super(effect);
        this.exileZoneIdString = effect.exileZoneIdString;
    }

    @Override
    public ShareTheSpoilsExileSingleCardEffect copy() { return new ShareTheSpoilsExileSingleCardEffect(this); }
}

class ShareTheSpoilsWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public ShareTheSpoilsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED) {
            if (event.hasApprovingIdentifier(MageIdentifier.ShareTheSpoilsWatcher)) {
                usedFrom.add(event.getAdditionalReference().getApprovingMageObjectReference());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean hasNotUsedAbilityThisTurn(MageObjectReference mor) { return !usedFrom.contains(mor); }
}
