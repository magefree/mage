
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class FaithsReward extends CardImpl {

    public FaithsReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}");


        // Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new FaithsRewardEffect());
        this.getSpellAbility().addWatcher(new FaithsRewardWatcher());
    }

    public FaithsReward(final FaithsReward card) {
        super(card);
    }

    @Override
    public FaithsReward copy() {
        return new FaithsReward(this);
    }
}

class FaithsRewardEffect extends OneShotEffect {

    FaithsRewardEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Return to the battlefield all permanent cards in your graveyard that were put there from the battlefield this turn";
    }

    FaithsRewardEffect(final FaithsRewardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FaithsRewardWatcher watcher = game.getState().getWatcher(FaithsRewardWatcher.class);
        if (watcher != null) {
            for (UUID id : watcher.getCards()) {
                Card c = game.getCard(id);
                if (c != null && c.isOwnedBy(source.getControllerId()) && game.getState().getZone(id) == Zone.GRAVEYARD) {
                    c.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FaithsRewardEffect copy() {
        return new FaithsRewardEffect(this);
    }
}

class FaithsRewardWatcher extends Watcher {
    private List<UUID> cards = new ArrayList<>();

    public FaithsRewardWatcher() {
        super(WatcherScope.GAME);
    }

    public FaithsRewardWatcher(final FaithsRewardWatcher watcher) {
        super(watcher);
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            cards.add(event.getTargetId());
        }
    }

    public List<UUID> getCards(){
        return cards;
    }

    @Override
    public FaithsRewardWatcher copy() {
        return new FaithsRewardWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
