package mage.cards.s;

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
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ShareTheSpoils extends CardImpl {

    // Number used to ensure that each cast of Share the Spoils has its own unique exile pile
    //      "Once Share the Spoils leaves the battlefield, players may no longer play the exiled cards.
    //       If Share the Spoils is destroyed and somehow returns to the battlefield,
    //       it’s a new object with no memory of the previously exiled cards."
    private static int exileNumber = 0;
    private final String exileZoneName;

    // Needed in order to test that the class works properly
    public static int getExileNumber() {
        return exileNumber;
    }

    public ShareTheSpoils(UUID ownderId, CardSetInfo setInfo) {
        // TODO: For some reason the text shown when no image is available is wrong.
        //       Shows only half the text and repeats itself.
        super(ownderId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Increment everytime a new copy is cast so that they don't share

        exileZoneName = "Share the Spoils " + ++exileNumber;

        // When Share the Spoils enters the battlefield or an opponent loses the game,
        // exile the top card of each player’s library.exile the top card of each player’s library.
        this.addAbility(new ShareTheSpoilsExileTrigger());

        // During each player’s turn, that player may play a land or cast a spell from among cards exiled with Share the Spoils
        Ability castAbility = new SimpleStaticAbility(new ShareTheSpoilsPlayCardExileEffect());
        // , and they may spend mana as though it were mana of any color to cast that spell.
        castAbility.addEffect(new ShareTheSpoilsSpendAnyManaEffect());
        // When they do, exile the top card of their library.
        // TODO

    }

    private ShareTheSpoils(final ShareTheSpoils card) {
        super(card);
        // Increment everytime a new copy is cast so that they don't share
        exileNumber++;
        exileZoneName = "Share the Spoils " + exileNumber + ", cast with any color";
    }

    @Override
    public ShareTheSpoils copy() {
        return new ShareTheSpoils(this);
    }

    // Based on Prosper Tome Bond
    class ShareTheSpoilsExileTrigger extends TriggeredAbilityImpl {

        ShareTheSpoilsExileTrigger() {
            super(Zone.BATTLEFIELD, new ShareTheSpoilsExileEffect());
        }

        private ShareTheSpoilsExileTrigger(final ShareTheSpoilsExileTrigger ability) {
            super(ability);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            // TODO: I don't know if this will cast when a player loses the game.
            //       1. Make sure it triggers when an opponent dies
            //       2. Change this so it doesn't trigger if you die.
            return event.getType() == GameEvent.EventType.LOSES ||
                    event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            return true;
        }

        @Override
        public TriggeredAbility copy() {
            return new ShareTheSpoilsExileTrigger(this);
        }

        @Override
        public String getTriggerPhrase() {
            return "When Share the Spoils enters the battlefield or an opponent loses the game";
        }
    }

    // TODO
    // Based on Author of Shadows
    class ShareTheSpoilsExileEffect extends OneShotEffect {

        public ShareTheSpoilsExileEffect() {
            super(Outcome.DrawCard);
            staticText = ", exile the top card of each player's library.";
        }

        public ShareTheSpoilsExileEffect(final ShareTheSpoilsExileEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            // Create an exile zone unique to this card
            ExileZone exileZone = game.getExile().createZone(CardUtil.getExileZoneId(exileZoneName, game), exileZoneName);

            PlayerList players = game.getState().getPlayersInRange(source.getControllerId(), game);
            for (UUID playerId : players) {
                Player player = game.getPlayer(playerId);

                if (player == null) { continue; }

                Card card = player.getLibrary().getFromTop(game);

                if (card == null) { continue; }

                player.moveCards(card, Zone.EXILED, source, game);
                game.getExile().moveToAnotherZone(card, game, exileZone);
                // TODO: How to make sure that the card is only playable while this is in play?
                //       I think I should get rid fo this card.
//                CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
            }
            return true;
        }

        @Override
        public ShareTheSpoilsExileEffect copy() {
            return new ShareTheSpoilsExileEffect(this);
        }
    }

    // TODO
    class ShareTheSpoilsPlayCardExileEffect extends AsThoughEffectImpl {
        private int currTurn = 0;
        private boolean playedCardWithShareTheSpoils = false;

        ShareTheSpoilsPlayCardExileEffect() {
            super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        }

        private ShareTheSpoilsPlayCardExileEffect(final ShareTheSpoilsPlayCardExileEffect effect) {
            super(effect);
        }

        @Override
        public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
            Card card = game.getCard(sourceId);

            if (card == null) { return false; }

            // Not in exile
            if (game.getState().getZone(card.getMainCard().getId()) != Zone.EXILED) { return false; }

            // Not a card exiled with this Share the Spoils
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(exileZoneName, game));
            if (!exileZone.contains(sourceId)) { return false; }

            // Have to play on your turn
            if (!game.getActivePlayerId().equals(source.getControllerId())) { return false; }

            // Check that they haven't played a card with Share the Spoils before
            if (currTurn < game.getTurnNum()) {
                currTurn = game.getTurnNum();
                playedCardWithShareTheSpoils = false;
                return true;
            } else {
                // TODO: How do I know if they've player a card?
                if (playedCardWithShareTheSpoils) {
                    return false;
                } else {
                    playedCardWithShareTheSpoils = true;
                    return true;
                }
            }
        }

        @Override
        public AsThoughEffect copy() {
            return new ShareTheSpoilsPlayCardExileEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
             return true;
        }
    }

    // Based on DraugrNecromancer
    static class ShareTheSpoilsSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

        ShareTheSpoilsSpendAnyManaEffect() {
            super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
            staticText = ", and they may spend mana as though it were mana of any color to cast that spell";
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
            return true;
        }

        @Override
        public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
            return mana.getFirstAvailable();
        }
    }
}