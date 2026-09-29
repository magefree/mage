package mage.watchers.common;

import mage.abilities.Ability;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.CardsDrawnThisTurnWatcher;
import mage.util.functions.MiracleCostModifierCreator;

import java.util.UUID;

public class MiracleGrantedWatcher extends Watcher {

    final private Zone zone;
    final private FilterCard filter;
    final private MiracleCostModifierCreator miracleCostModifierCreator;
    final private String costText;

    public MiracleGrantedWatcher(Zone zone, FilterCard filter, MiracleCostModifierCreator miracleCostModifierCreator, String costText) {
        super(WatcherScope.CARD, true);
        this.zone = zone;
        this.filter = filter;
        this.miracleCostModifierCreator = miracleCostModifierCreator;
        this.costText = costText;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UNTAP_STEP_PRE) {
            this.reset();
            return;
        }

        // inital card draws do not trigger miracle so check that phase != null
        if (game.getPhase() != null && event.getType() == GameEvent.EventType.DREW_CARD && this.zone.match(game.getState().getZone(this.getSourceId()))) {
            final UUID playerId = event.getPlayerId();
            final Card card = game.getCard(event.getTargetId());
            if (playerId != null && card != null && playerId.equals(this.getControllerId()) && game.getState().getWatcher(CardsDrawnThisTurnWatcher.class).getCardsDrawnThisTurn(playerId) == 1 && this.filter.match(card, game)) {
                final Player controller = game.getPlayer(playerId);
                if (controller != null) {
                    final Cards cards = new CardsImpl(card);
                    controller.lookAtCards("Miracle", cards, game);
                    final Ability ability = new MiracleAbility(this.miracleCostModifierCreator, this.costText);
                    if (controller.chooseUse(Outcome.Benefit, "Reveal " + card.getLogName() + " to be able to use Miracle?", ability, game)) {
                        game.getState().addOtherAbility(card, ability);
                        controller.revealCards("Miracle", cards, game);
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.MIRACLE_CARD_REVEALED, card.getId(), ability, controller.getId()));
                    }
                }
            }
        }
    }
}
