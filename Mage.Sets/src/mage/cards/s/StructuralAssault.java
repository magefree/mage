package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author weirddan455
 */
public final class StructuralAssault extends CardImpl {

    public StructuralAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Destroy all artifacts, then Structural Assault deals damage to each creature equal to the number of artifacts that were put into graveyards from the battlefield this turn.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS));
        this.getSpellAbility().addEffect(new StructuralAssaultEffect());
        this.getSpellAbility().addWatcher(new StructuralAssaultWatcher());
    }

    private StructuralAssault(final StructuralAssault card) {
        super(card);
    }

    @Override
    public StructuralAssault copy() {
        return new StructuralAssault(this);
    }
}

// CardsPutIntoGraveyardWatcher does not count tokens so custom watcher is needed.
class StructuralAssaultWatcher extends Watcher {

    private int artifactsDied = 0;

    public StructuralAssaultWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent() && zEvent.getTarget().isArtifact(game)) {
                artifactsDied++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        artifactsDied = 0;
    }

    public int getArtifactsDied() {
        return artifactsDied;
    }
}

class StructuralAssaultEffect extends OneShotEffect {

    public StructuralAssaultEffect() {
        super(Outcome.Damage);
        this.staticText = ", then {this} deals damage to each creature equal to the number of artifacts that were put into graveyards from the battlefield this turn";
    }

    private StructuralAssaultEffect(final StructuralAssaultEffect effect) {
        super(effect);
    }

    @Override
    public StructuralAssaultEffect copy() {
        return new StructuralAssaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StructuralAssaultWatcher watcher = game.getState().getWatcher(StructuralAssaultWatcher.class);
        if (watcher != null) {
            int artifacts = watcher.getArtifactsDied();
            if (artifacts > 0) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game)) {
                    permanent.damage(artifacts, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
