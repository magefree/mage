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

// TODO
//  Card in exile isn't highlighted as castable
//  Casting a card from exile does not exile a replacement
//  Play text still tells you to pay the colored mana when casting from exile
//  Check that it's no longer playable once share the spoils is removed
//  As any mana is not working yet
//  Exile window sticks around after share the spoils has been removed

/**
 * @author Alex-Vasile
 */

public final class ShareTheSpoils extends CardImpl {

    // Number used to ensure that each cast of Share the Spoils has its own unique exile pile
    //      "Once Share the Spoils leaves the battlefield, players may no longer play the exiled cards.
    //       If Share the Spoils is destroyed and somehow returns to the battlefield,
    //       it’s a new object with no memory of the previously exiled cards."
    private final String exileZoneId;

    public ShareTheSpoils(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Increment everytime a new copy is cast so that they don't share
        exileZoneId = this.getId().toString();

        // When Share the Spoils enters the battlefield or an opponent loses the game,
        // exile the top card of each player’s library.exile the top card of each player’s library.
        this.addAbility(new ShareTheSpoilsExileETBAndPlayerLossAbility());

        // During each player’s turn,
        Ability castAbility = new SimpleStaticAbility(new ShareTheSpoilsPlayExiledCardEffect());
        // that player may play a land or cast a spell from among cards exiled with Share the Spoils,
        // and they may spend mana as though it were mana of any color to cast that spell.
        castAbility.addEffect(new ShareTheSpoilsSpendAnyManaEffect());
        castAbility.setIdentifier(MageIdentifier.ShareTheSpoilsWatcher);
        this.addAbility(castAbility, new ShareTheSpoilsWatcher());

        // When they do, exile the top card of their library.
        Ability exileCardWhenPlayedACard = new ShareTheSpoilsExileCardWhenPlayACardAbility();
        this.addAbility(exileCardWhenPlayedACard);
    }

    private ShareTheSpoils(final ShareTheSpoils card) {
        super(card);
        exileZoneId = card.getId().toString();
    }

    @Override
    public ShareTheSpoils copy() {
        return new ShareTheSpoils(this);
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
            // Something entered the battlefield, and it should only go of when this card enters
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

        public ShareTheSpoilsExileCardFromEveryoneEffect() {
            super(Outcome.Exile);
            staticText = ", exile the top card of each player's library.";
        }

        public ShareTheSpoilsExileCardFromEveryoneEffect(final ShareTheSpoilsExileCardFromEveryoneEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            if (source == null) { return false; }

            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneId, game));

            if (exileZone == null) {
                exileZone = game.getExile().createZone(
                        CardUtil.getExileZoneId(exileZoneId, game),
                        "Share the Spoils");
            }
            // Create an exile zone unique to this card


            PlayerList players = game.getState().getPlayersInRange(source.getControllerId(), game);
            for (UUID playerId : players) {
                Player player = game.getPlayer(playerId);

                if (player == null) { continue; }

                Card topLibraryCard = player.getLibrary().getFromTop(game);

                if (topLibraryCard == null) { continue; }

                // TODO: Should I use moveCardsToExile instead?
                player.moveCards(topLibraryCard, Zone.EXILED, source, game);
                game.getExile().moveToAnotherZone(topLibraryCard, game, exileZone);
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
            super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
            // TODO: All the text is here rather than spread out since I can't figure out how to get rid of the new line
            //       that putting the "When they do, exile the top card of their library.", inside the other triggered
            //       ability would create, and which isn't in the Oracle text.
            staticText = "During each player’s turn, " +
                    "that player may play a land or cast a spell from among cards exiled with Share the Spoils, " +
                    "and they may spend mana as though it were mana of any color to cast that spell. " +
                    "When they do, exile the top card of their library";
        }

        private ShareTheSpoilsPlayExiledCardEffect(final ShareTheSpoilsPlayExiledCardEffect effect) {
            super(effect);
        }

        @Override
        public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
            Card card = game.getCard(sourceId);

            if (card == null) { return false; }

            // Not in exile
            if (game.getState().getZone(card.getMainCard().getId()) != Zone.EXILED) { return false; }

            // Not a card exiled with this Share the Spoils
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneId, game));
            if (exileZone == null) { return false; }
            if (!exileZone.contains(sourceId)) { return false; }

            // Have to play on your turn
            if (!game.getActivePlayerId().equals(source.getControllerId())) { return false; }

            // TODO: what is this line doing?
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (sourceObject == null) { return false; }

            ShareTheSpoilsWatcher watcher = game.getState().getWatcher(ShareTheSpoilsWatcher.class);

            return watcher.hasNotUsedAbilityThisTurn(new MageObjectReference(sourceObject, game));
        }

        @Override
        public AsThoughEffect copy() {
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
            Card card = game.getCard(sourceId);
            if (card == null) { return false; }

            // Check Exile
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneId, game));
            if (exileZone == null) { return false; }
            if (exileZone.contains(sourceId)) { return true; }

            // Check Stack
            CardState cardState;
            if (card instanceof SplitCard) {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            } else if (card instanceof AdventureCard) {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            } else if (card instanceof ModalDoubleFacesCard) {
                cardState = game.getLastKnownInformationCard(((ModalDoubleFacesCard) card).getLeftHalfCard().getId(), Zone.EXILED);
            } else {
                cardState = game.getLastKnownInformationCard(card.getId(), Zone.EXILED);
            }

            if (cardState == null) { return false; }

            // TODO: How to figure out what exile zone this card came from
            return true;
        }

        @Override
        public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
            return mana.getFirstAvailable();
        }
    }

    //-- Exile when a card is played that was exiled with Share the Spoil's  --//
    class ShareTheSpoilsExileCardWhenPlayACardAbility extends TriggeredAbilityImpl {

        ShareTheSpoilsExileCardWhenPlayACardAbility() {
            super(Zone.BATTLEFIELD, new ShareTheSpoilsExileSingleCardEffect());
        }

        private ShareTheSpoilsExileCardWhenPlayACardAbility(final ShareTheSpoilsExileCardWhenPlayACardAbility ability) {
            super(ability);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return false;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (!(event.getType() == GameEvent.EventType.SPELL_CAST ||
                    event.getType() == GameEvent.EventType.PLAY_LAND)) {
                return false;
            }
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneId, game));
            if (exileZone == null) { return false; }
            return exileZone.contains(sourceId);
        }

        @Override
        public TriggeredAbility copy() {
            return new ShareTheSpoilsExileCardWhenPlayACardAbility(this);
        }
    }

    class ShareTheSpoilsExileSingleCardEffect extends OneShotEffect {

        ShareTheSpoilsExileSingleCardEffect() {
            super(Outcome.Exile);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            if (source == null) { return false; }

            // Not a card exiled with this Share the Spoils
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneId, game));
            if (exileZone == null) { return false; }

            Player player = game.getPlayer(source.getControllerId());
            if (player == null) { return false; }

            Card topLibraryCard = player.getLibrary().getFromTop(game);
            if (topLibraryCard == null) { return false; }

            // TODO: Should I use moveCardsToExile instead?
            player.moveCards(topLibraryCard, Zone.EXILED, source, game);
            game.getExile().moveToAnotherZone(topLibraryCard, game, exileZone);

            return true;
        }

        private ShareTheSpoilsExileSingleCardEffect(final ShareTheSpoilsExileSingleCardEffect effect) { super(effect); }

        @Override
        public ShareTheSpoilsExileSingleCardEffect copy() { return new ShareTheSpoilsExileSingleCardEffect(this); }
    }
}

class ShareTheSpoilsWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public ShareTheSpoilsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED) {
            // TODO: What does this if statement check for?
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