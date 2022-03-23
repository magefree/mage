package mage.cards.p;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author noahg
 */
public final class PrematureBurial extends CardImpl {

    public PrematureBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Destroy target nonblack creature that entered the battlefield since your last turn ended.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new ETBSinceYourLastTurnTarget(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addWatcher(new ETBSinceYourLastTurnWatcher());
    }

    private PrematureBurial(final PrematureBurial card) {
        super(card);
    }

    @Override
    public PrematureBurial copy() {
        return new PrematureBurial(this);
    }
}

class ETBSinceYourLastTurnTarget extends TargetCreaturePermanent {

    public ETBSinceYourLastTurnTarget(FilterCreaturePermanent filter) {
        super(filter);
        this.targetName = "nonblack creature that entered the battlefield since your last turn ended";
    }

    public ETBSinceYourLastTurnTarget(ETBSinceYourLastTurnTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        ETBSinceYourLastTurnWatcher watcher = game.getState().getWatcher(ETBSinceYourLastTurnWatcher.class);
        if (watcher != null) {
            if (watcher.enteredSinceLastTurn(controllerId, new MageObjectReference(id, game))) {
                return super.canTarget(controllerId, id, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        MageObject targetSource = game.getObject(source);
        ETBSinceYourLastTurnWatcher watcher = game.getState().getWatcher(ETBSinceYourLastTurnWatcher.class);
        if (targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    if (watcher != null && watcher.enteredSinceLastTurn(sourceControllerId, new MageObjectReference(permanent.getId(), game))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ETBSinceYourLastTurnTarget copy() {
        return new ETBSinceYourLastTurnTarget(this);
    }
}

class ETBSinceYourLastTurnWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> playerToETBMap;

    public ETBSinceYourLastTurnWatcher() {
        super(WatcherScope.GAME);
        this.playerToETBMap = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_POST) {
            playerToETBMap.put(event.getPlayerId(), new HashSet<>());
        } else if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent etbPermanent = game.getPermanent(event.getTargetId());
            if (etbPermanent != null) {
                for (UUID player : game.getPlayerList()) {
                    if (!playerToETBMap.containsKey(player)) {
                        playerToETBMap.put(player, new HashSet<>());
                    }
                    playerToETBMap.get(player).add(new MageObjectReference(etbPermanent.getBasicMageObject(game), game));
                }
            }
        }
    }

    public boolean enteredSinceLastTurn(UUID player, MageObjectReference mor) {
        return playerToETBMap.get(player).contains(mor);
    }
}
