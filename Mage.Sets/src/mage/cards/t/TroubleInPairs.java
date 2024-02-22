package mage.cards.t;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SkipExtraTurnsAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsDrawnThisTurnWatcher;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.*;

/**
 * @author PurpleCrowbar
 */
public final class TroubleInPairs extends CardImpl {

    public TroubleInPairs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // If an opponent would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SkipExtraTurnsAbility(true));

        // Whenever an opponent attacks you with two or more creatures, draws their second card each turn, or casts their second spell each turn, you draw a card.
        this.addAbility(new TroubleInPairsTriggeredAbility());
    }

    private TroubleInPairs(final TroubleInPairs card) {
        super(card);
    }

    @Override
    public TroubleInPairs copy() {
        return new TroubleInPairs(this);
    }
}

class TroubleInPairsTriggeredAbility extends TriggeredAbilityImpl {

    TroubleInPairsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
    }

    private TroubleInPairsTriggeredAbility(final TroubleInPairsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TroubleInPairsTriggeredAbility copy() {
        return new TroubleInPairsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS
                || event.getType() == GameEvent.EventType.DREW_CARD
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller == null || !game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            // Whenever an opponent attacks you with two or more creatures
            case DECLARED_ATTACKERS:
                return game
                        .getCombat()
                        .getAttackers()
                        .stream()
                        .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                        .filter(getControllerId()::equals)
                        .count() >= 2;
            // Whenever an opponent draws their second card each turn
            case DREW_CARD:
                CardsDrawnThisTurnWatcher drawWatcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
                return drawWatcher != null && drawWatcher.getCardsDrawnThisTurn(event.getPlayerId()) == 2;
            // Whenever an opponent casts their second spell each turn
            case SPELL_CAST:
                CastSpellLastTurnWatcher spellWatcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
                return spellWatcher != null && spellWatcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
        }

        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you with two or more creatures, draws their second " +
                "card each turn, or casts their second spell each turn, you draw a card.";
    }
}
