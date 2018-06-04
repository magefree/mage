
package mage.cards.c;

import java.util.*;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 * @author BetaSteward
 */
public final class CathedralMembrane extends CardImpl {

    public CathedralMembrane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W/P}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // <i>({W/P} can be paid with either {W} or 2 life.)</i>
        this.addAbility(DefenderAbility.getInstance());

        // When Cathedral Membrane dies during combat, it deals 6 damage to each creature it blocked this combat.
        this.addAbility(new CathedralMembraneAbility(), new CathedralMembraneWatcher());

    }

    public CathedralMembrane(final CathedralMembrane card) {
        super(card);
    }

    @Override
    public CathedralMembrane copy() {
        return new CathedralMembrane(this);
    }
}

class CathedralMembraneAbility extends ZoneChangeTriggeredAbility {

    public CathedralMembraneAbility() {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, new CathedralMembraneEffect(), "When {this} dies during combat, ", false);
    }

    public CathedralMembraneAbility(CathedralMembraneAbility ability) {
        super(ability);
    }

    @Override
    public CathedralMembraneAbility copy() {
        return new CathedralMembraneAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            if (game.getPhase().getType() == TurnPhase.COMBAT) {
                return true;
            }
        }
        return false;
    }

}

class CathedralMembraneEffect extends OneShotEffect {

    public CathedralMembraneEffect() {
        super(Outcome.Damage);
        staticText = "it deals 6 damage to each creature it blocked this combat";
    }

    public CathedralMembraneEffect(final CathedralMembraneEffect effect) {
        super(effect);
    }

    @Override
    public CathedralMembraneEffect copy() {
        return new CathedralMembraneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CathedralMembraneWatcher watcher = (CathedralMembraneWatcher) game.getState().getWatchers().get(CathedralMembraneWatcher.class.getSimpleName(), source.getSourceId());
        if (watcher != null) {
            for (UUID uuid : watcher.blockedCreatures) {
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null) {
                    permanent.damage(6, source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }
}

class CathedralMembraneWatcher extends Watcher {

    public Set<UUID> blockedCreatures = new HashSet<>();

    public CathedralMembraneWatcher() {
        super(CathedralMembraneWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    public CathedralMembraneWatcher(final CathedralMembraneWatcher watcher) {
        super(watcher);
        this.blockedCreatures.addAll(watcher.blockedCreatures);
    }

    @Override
    public CathedralMembraneWatcher copy() {
        return new CathedralMembraneWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED && event.getSourceId().equals(sourceId)) {
            blockedCreatures.add(event.getTargetId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockedCreatures.clear();
    }

}
