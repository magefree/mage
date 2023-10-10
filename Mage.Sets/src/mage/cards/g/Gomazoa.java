
package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class Gomazoa extends CardImpl {

    public Gomazoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.JELLYFISH);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {tap}: Put Gomazoa and each creature it's blocking on top of their owners' libraries, then those players shuffle their libraries.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GomazoaEffect(), new TapSourceCost()), new BlockedByWatcher());

    }

    private Gomazoa(final Gomazoa card) {
        super(card);
    }

    @Override
    public Gomazoa copy() {
        return new Gomazoa(this);
    }
}

class GomazoaEffect extends OneShotEffect {

    public GomazoaEffect() {
        super(Outcome.Neutral);
        this.staticText = "Put {this} and each creature it's blocking on top of their owners' libraries, then those players shuffle";
    }

    private GomazoaEffect(final GomazoaEffect effect) {
        super(effect);
    }

    @Override
    public GomazoaEffect copy() {
        return new GomazoaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> players = new ArrayList<>();
            Permanent gomazoa = game.getPermanent(source.getSourceId());
            if (gomazoa != null) {
                controller.moveCardToLibraryWithInfo(gomazoa, source, game, Zone.BATTLEFIELD, true, true);
                players.add(gomazoa.getOwnerId());
            }

            BlockedByWatcher watcher = game.getState().getWatcher(BlockedByWatcher.class, source.getSourceId());

            for (UUID blockedById : watcher.getBlockedByWatcher()) {
                Permanent blockedByGomazoa = game.getPermanent(blockedById);
                if (blockedByGomazoa != null && blockedByGomazoa.isAttacking()) {
                    players.add(blockedByGomazoa.getOwnerId());
                    Player owner = game.getPlayer(blockedByGomazoa.getOwnerId());
                    if (owner != null) {
                        owner.moveCardToLibraryWithInfo(blockedByGomazoa, source, game, Zone.BATTLEFIELD, true, true);
                    }
                }
            }
            for (UUID player : players) {
                Player owner = game.getPlayer(player);
                if (owner != null) {
                    owner.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;

    }
}

class BlockedByWatcher extends Watcher {

    public List<UUID> getBlockedByWatcher() {
        return blockedByWatcher;
    }

    private List<UUID> blockedByWatcher = new ArrayList<>();

    public BlockedByWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            if (sourceId.equals(event.getSourceId()) && !blockedByWatcher.contains(event.getTargetId())) {
                blockedByWatcher.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockedByWatcher.clear();
    }
}
