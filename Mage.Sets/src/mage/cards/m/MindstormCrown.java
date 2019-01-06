
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class MindstormCrown extends CardImpl {

    public MindstormCrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of your upkeep, draw a card if you had no cards in hand at the beginning of this turn. If you had a card in hand, Mindstorm Crown deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MindstormCrownEffect(), TargetController.YOU, false), new MindstormCrownWatcher());
    }

    public MindstormCrown(final MindstormCrown card) {
        super(card);
    }

    @Override
    public MindstormCrown copy() {
        return new MindstormCrown(this);
    }
}

class MindstormCrownEffect extends OneShotEffect {

    MindstormCrownEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    MindstormCrownEffect(final MindstormCrownEffect effect) {
        super(effect);
    }

    @Override
    public MindstormCrownEffect copy() {
        return new MindstormCrownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player == null) {
            return false;
        }
        MindstormCrownWatcher watcher = game.getState().getWatcher(MindstormCrownWatcher.class);
        if (watcher != null && watcher.getCardsInHandCount() == 0) {
            player.drawCards(1, game);
        } else {
            if (permanent != null) {
                player.damage(2, permanent.getId(), game, false, true);
            }
        }
        return true;
    }
}

class MindstormCrownWatcher extends Watcher {

    private int cardsInHandCount;

    public MindstormCrownWatcher() {
        super(MindstormCrownWatcher.class, WatcherScope.GAME);
    }

    public MindstormCrownWatcher(final MindstormCrownWatcher watcher) {
        super(watcher);
        cardsInHandCount = watcher.cardsInHandCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE
                && game.getPhase() != null) {
            Player player = game.getPlayer(game.getActivePlayerId());
            int cardsInHand = 0;
            if (player != null) {
                cardsInHand = player.getHand().size();
            }
            cardsInHandCount = cardsInHand;
        }
    }

    public int getCardsInHandCount() {
        return cardsInHandCount;
    }

    @Override
    public void reset() {
        cardsInHandCount = 0;
    }

    @Override
    public MindstormCrownWatcher copy() {
        return new MindstormCrownWatcher(this);
    }
}
