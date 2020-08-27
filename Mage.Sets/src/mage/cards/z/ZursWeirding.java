package mage.cards.z;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.PlayWithHandRevealedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author Quercitron
 */
public final class ZursWeirding extends CardImpl {

    public ZursWeirding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Players play with their hands revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithHandRevealedEffect(TargetController.ANY)));

        // If a player would draw a card, they reveal it instead. Then any other player may pay 2 life. If a player does, put that card into its owner's graveyard. Otherwise, that player draws a card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ZursWeirdingReplacementEffect()));
    }

    public ZursWeirding(final ZursWeirding card) {
        super(card);
    }

    @Override
    public ZursWeirding copy() {
        return new ZursWeirding(this);
    }
}

class ZursWeirdingReplacementEffect extends ReplacementEffectImpl {

    ZursWeirdingReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "If a player would draw a card, they reveal it instead. Then any other player may pay 2 life. If a player does, put that card into its owner's graveyard. Otherwise, that player draws a card.";
    }

    private ZursWeirdingReplacementEffect(final ZursWeirdingReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ZursWeirdingReplacementEffect copy() {
        return new ZursWeirdingReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        boolean paid = false;
        Player player = game.getPlayer(event.getTargetId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player == null
                || sourceObject == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        // Reveals it instead
        player.revealCards(sourceObject.getIdName() + " next draw of " + player.getName() + " (" + game.getTurnNum() + '|' + game.getPhase().getType() + ')', new CardsImpl(card), game);

        // Then any other player may pay 2 life. If a player does, put that card into its owner's graveyard
        String message = "Pay 2 life to put " + card.getLogName() + " into " + player.getLogName() + " graveyard?";

        for (UUID playerId : game.getState().getPlayersInRange(player.getId(), game)) {
            if (playerId.equals(player.getId())) {
                continue;
            }
            Player otherPlayer = game.getPlayer(playerId);
            if (otherPlayer.canPayLifeCost(source)
                    && otherPlayer.getLife() >= 2) {
                PayLifeCost lifeCost = new PayLifeCost(2);
                while (otherPlayer.canRespond()
                        && !paid
                        && otherPlayer.chooseUse(Outcome.Benefit, message, source, game)) {
                    paid = lifeCost.pay(source, game, source.getSourceId(), otherPlayer.getId(), false, null);
                }
                if (paid) {
                    player.moveCards(card, Zone.GRAVEYARD, source, game);
                    return true;
                }
            }
        }
        // This is still replacing the draw, so we still return true
        player.drawCards(1, source.getSourceId(), game, event.getAppliedEffects());
        return true;
    }
}
