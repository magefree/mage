
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author anonymous
 */
public final class NoRestForTheWicked extends CardImpl {

    public NoRestForTheWicked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // Sacrifice No Rest for the Wicked: Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NoRestForTheWickedEffect(), new SacrificeSourceCost());
        Watcher watcher = new NoRestForTheWickedWatcher();
        addAbility(ability, watcher);
    }

    public NoRestForTheWicked(final NoRestForTheWicked card) {
        super(card);
    }

    @Override
    public NoRestForTheWicked copy() {
        return new NoRestForTheWicked(this);
    }
}

class NoRestForTheWickedEffect extends OneShotEffect {

    NoRestForTheWickedEffect() {
        super(Outcome.Sacrifice);
        staticText = "Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn";
    }

    NoRestForTheWickedEffect(final NoRestForTheWickedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        NoRestForTheWickedWatcher watcher = (NoRestForTheWickedWatcher) game.getState().getWatchers().get(NoRestForTheWickedWatcher.class.getSimpleName());
        Player controller = game.getPlayer(source.getControllerId());
        if (watcher != null && controller != null) {
            Cards cardsToHand = new CardsImpl();
            for (UUID cardId : watcher.cards) {
                Card c = game.getCard(cardId);
                if (c != null) {
                    if (game.getState().getZone(cardId) == Zone.GRAVEYARD
                            && c.isCreature()
                            && c.isOwnedBy(source.getControllerId())) {
                        cardsToHand.add(c);
                    }
                }
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }

    @Override
    public NoRestForTheWickedEffect copy() {
        return new NoRestForTheWickedEffect(this);

    }
}

class NoRestForTheWickedWatcher extends Watcher {

    List<UUID> cards;

    public NoRestForTheWickedWatcher() {
        super(NoRestForTheWickedWatcher.class.getSimpleName(), WatcherScope.GAME);
        this.cards = new ArrayList<>();
    }

    public NoRestForTheWickedWatcher(final NoRestForTheWickedWatcher watcher) {
        super(watcher);
        this.cards = new ArrayList<>();
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()) {
            //400.3 Intercept only the controller's events
            cards.add(event.getTargetId());
        }
    }

    @Override
    public NoRestForTheWickedWatcher copy() {
        return new NoRestForTheWickedWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
