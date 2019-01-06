
package mage.cards.s;

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
 * @author LevelX2
 */
public final class SecondSunrise extends CardImpl {

    public SecondSunrise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");


        // Each player returns to the battlefield all artifact, creature, enchantment, and land cards in their graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new SecondSunriseEffect());
        this.getSpellAbility().addWatcher(new SecondSunriseWatcher());
    }

    public SecondSunrise(final SecondSunrise card) {
        super(card);
    }

    @Override
    public SecondSunrise copy() {
        return new SecondSunrise(this);
    }
}

class SecondSunriseEffect extends OneShotEffect {

    SecondSunriseEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Each player returns to the battlefield all artifact, creature, enchantment, and land cards in their graveyard that were put there from the battlefield this turn";
    }

    SecondSunriseEffect(final SecondSunriseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SecondSunriseWatcher watcher = game.getState().getWatcher(SecondSunriseWatcher.class);
        if (watcher != null) {
            for (UUID id : watcher.cards) {
                Card c = game.getCard(id);
                if (c != null && game.getState().getZone(id) == Zone.GRAVEYARD) {
                    if (c.isArtifact() || c.isCreature() ||
                        c.isEnchantment() || c.isLand())
                    c.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SecondSunriseEffect copy() {
        return new SecondSunriseEffect(this);
    }
}

class SecondSunriseWatcher extends Watcher {
    List<UUID> cards = new ArrayList<>();

    public SecondSunriseWatcher() {
        super(SecondSunriseWatcher.class, WatcherScope.GAME);
    }

    public SecondSunriseWatcher(final SecondSunriseWatcher watcher) {
        super(watcher);
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            cards.add(event.getTargetId());
        }
    }

    @Override
    public SecondSunriseWatcher copy() {
        return new SecondSunriseWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
