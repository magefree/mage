
package mage.cards.i;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author jeffwadsworth
 */
public final class InfernoTrap extends CardImpl {

    public InfernoTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.subtype.add(SubType.TRAP);

        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{R}"), InfernoTrapCondition.instance), new InfernoTrapWatcher());

        // Inferno Trap deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InfernoTrap(final InfernoTrap card) {
        super(card);
    }

    @Override
    public InfernoTrap copy() {
        return new InfernoTrap(this);
    }
}

enum InfernoTrapCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        InfernoTrapWatcher watcher = game.getState().getWatcher(InfernoTrapWatcher.class);
        if (watcher != null) {
            Set<MageObjectReference> damagingCreatures = watcher.getDamagingCreatures(source.getControllerId());
            return damagingCreatures.size() > 1;
        }
        return false;
    }

    @Override
    public String toString() {
        return "If you've been dealt damage by two or more creatures this turn";
    }
}

class InfernoTrapWatcher extends Watcher {

    private Map<UUID, Set<MageObjectReference>> playerDamagedByCreature = new HashMap<>();

    public InfernoTrapWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && event.getTargetId().equals(controllerId)) {
            Permanent damageBy = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (damageBy != null && damageBy.isCreature(game)) {
                Set<MageObjectReference> damagingCreatures = playerDamagedByCreature.getOrDefault(event.getTargetId(), new HashSet<>());

                MageObjectReference damagingCreature = new MageObjectReference(damageBy, game);
                damagingCreatures.add(damagingCreature);
                playerDamagedByCreature.put(event.getTargetId(), damagingCreatures);

            }
        }
    }

    public Set<MageObjectReference> getDamagingCreatures(UUID playerId) {
        return playerDamagedByCreature.getOrDefault(playerId, new HashSet<>());
    }

    @Override
    public void reset() {
        super.reset();
        playerDamagedByCreature.clear();
    }

}
