
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ArchiveTrap extends CardImpl {

    public ArchiveTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        this.subtype.add(SubType.TRAP);

        // If an opponent searched their library this turn, you may pay {0} rather than pay Archive Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new GenericManaCost(0), OpponentSearchesLibCondition.instance), new ArchiveTrapWatcher());

        // Target opponent puts the top thirteen cards of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new PutLibraryIntoGraveTargetEffect(13));
    }

    public ArchiveTrap(final ArchiveTrap card) {
        super(card);
    }

    @Override
    public ArchiveTrap copy() {
        return new ArchiveTrap(this);
    }
}

class ArchiveTrapWatcher extends Watcher {

    Set<UUID> playerIds = new HashSet<>();

    public ArchiveTrapWatcher() {
        super(ArchiveTrapWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ArchiveTrapWatcher(final ArchiveTrapWatcher watcher) {
        super(watcher);
        this.playerIds.addAll(watcher.playerIds);
    }

    @Override
    public ArchiveTrapWatcher copy() {
        return new ArchiveTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.LIBRARY_SEARCHED
                && event.getTargetId().equals(event.getPlayerId())) { // player searched own library
            playerIds.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerIds.clear();
    }

    public Set<UUID> getPlayersSearchedLibrary() {
        return playerIds;
    }
}

enum OpponentSearchesLibCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ArchiveTrapWatcher watcher = (ArchiveTrapWatcher) game.getState().getWatchers().get(ArchiveTrapWatcher.class.getSimpleName());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && watcher != null) {
            for (UUID playerId : watcher.getPlayersSearchedLibrary()) {
                if (game.isOpponent(controller, playerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "If an opponent searched their library this turn";
    }

}
