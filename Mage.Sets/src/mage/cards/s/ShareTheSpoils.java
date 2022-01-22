package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.*;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.CardState;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ShareTheSpoils extends CardImpl {

    public ShareTheSpoils(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // When Share the Spoils enters the battlefield or an opponent loses the game,
        // exile the top card of each player’s library.exile the top card of each player’s library.
        this.addAbility(new ShareTheSpoilsExileTrigger());

        // During each player’s turn, that player may play a land or cast a spell from among cards exiled with Share the Spoils
        Ability castAbility = new SimpleStaticAbility(new ShareTheSpoilsPlayCardFromExileEffect());
        // , and they may spend mana as though it were mana of any color to cast that spell.
        castAbility.addEffect(new ShareTheSpoilsSpendAnyManaEffect());
        // When they do, exile the top card of their library.
        // TODO
        // that player may play a land or cast a spell, i.e. only one card max
        // TODO

    }

    private ShareTheSpoils(final ShareTheSpoils card) {
        super(card);
    }

    @Override
    public ShareTheSpoils copy() {
        return new ShareTheSpoils(this);
    }
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
        return "When Share the Spoils enters the battlefield or an opponent loses the game, " +
                "exile the top card of each player's library.";
    }
}

// Based on Cunning Rhetoric
class ShareTheSpoilsExileEffect extends OneShotEffect {

    public ShareTheSpoilsExileEffect() {
        super(Outcome.DrawCard);
        staticText = "Exile the top card of each player's library.";
    }

    public ShareTheSpoilsExileEffect(final ShareTheSpoilsExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);

            if (player == null) { continue; }

            Card card = player.getLibrary().getFromTop(game);

            if (card == null) { continue; }

            player.moveCards(card, Zone.EXILED, source, game);
            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        }
        return true;
    }

    @Override
    public ShareTheSpoilsExileEffect copy() {
        return new ShareTheSpoilsExileEffect(this);
    }
}


class ShareTheSpoilsPlayCardFromExileEffect extends AsThoughEffectImpl {

    ShareTheSpoilsPlayCardFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    private ShareTheSpoilsPlayCardFromExileEffect(final ShareTheSpoilsPlayCardFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!game.getActivePlayerId().equals(source.getControllerId())) {
            // TODO: Limit each player to only one per turn
            return true;
        }
        return false;
    }

    @Override
    public AsThoughEffect copy() {
        return new ShareTheSpoilsPlayCardFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
         return true;
    }
}

// Heavily based on DraugrNecromancer
class ShareTheSpoilsSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

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
        if (mana.getSourceObject().isSnow()) {
            return mana.getFirstAvailable();
        }
        return null;
    }
}
