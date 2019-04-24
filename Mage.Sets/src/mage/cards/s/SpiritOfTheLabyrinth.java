
package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 *
 * @author LevelX2
 */
public final class SpiritOfTheLabyrinth extends CardImpl {

    public SpiritOfTheLabyrinth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Each player can't draw more than one card each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpiritOfTheLabyrinthEffect()), new SpiritOfTheLabyrinthWatcher());        
        
    }

    public SpiritOfTheLabyrinth(final SpiritOfTheLabyrinth card) {
        super(card);
    }

    @Override
    public SpiritOfTheLabyrinth copy() {
        return new SpiritOfTheLabyrinth(this);
    }
}

class SpiritOfTheLabyrinthWatcher extends Watcher {

    private final Set<UUID> playersThatDrewCard;
    
    public SpiritOfTheLabyrinthWatcher() {
        super(SpiritOfTheLabyrinthWatcher.class.getSimpleName(), WatcherScope.GAME);
        this.playersThatDrewCard = new HashSet<>();
    }

    public SpiritOfTheLabyrinthWatcher(final SpiritOfTheLabyrinthWatcher watcher) {
        super(watcher);
        this.playersThatDrewCard = new HashSet<>();
        playersThatDrewCard.addAll(watcher.playersThatDrewCard);
    }

    @Override
    public SpiritOfTheLabyrinthWatcher copy() {
        return new SpiritOfTheLabyrinthWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD ) {
                playersThatDrewCard.add(event.getPlayerId());

        }
    }

    @Override
    public void reset() {
        super.reset();
        playersThatDrewCard.clear();
    }
    
    public boolean hasPlayerDrewCardThisTurn(UUID playerId) {
        return playersThatDrewCard.contains(playerId);
    }

}

class SpiritOfTheLabyrinthEffect extends ContinuousRuleModifyingEffectImpl {

    public SpiritOfTheLabyrinthEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Each player can't draw more than one card each turn";
    }

    public SpiritOfTheLabyrinthEffect(final SpiritOfTheLabyrinthEffect effect) {
        super(effect);
    }

    @Override
    public SpiritOfTheLabyrinthEffect copy() {
        return new SpiritOfTheLabyrinthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        SpiritOfTheLabyrinthWatcher watcher = (SpiritOfTheLabyrinthWatcher) game.getState().getWatchers().get(SpiritOfTheLabyrinthWatcher.class.getSimpleName());
        if (watcher != null && watcher.hasPlayerDrewCardThisTurn(event.getPlayerId())) {
            return true;
        }
        return false;
    }

}
