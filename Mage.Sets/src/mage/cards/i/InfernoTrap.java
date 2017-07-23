/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.i;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 * @author jeffwadsworth
 */
public class InfernoTrap extends CardImpl {

    public InfernoTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.subtype.add("Trap");

        // If you've been dealt damage by two or more creatures this turn, you may pay {R} rather than pay Inferno Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl("{R}"), InfernoTrapCondition.instance), new InfernoTrapWatcher());

        // Inferno Trap deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public InfernoTrap(final InfernoTrap card) {
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
        InfernoTrapWatcher watcher = (InfernoTrapWatcher) game.getState().getWatchers().get(InfernoTrapWatcher.class.getSimpleName());
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

    Map<UUID, Set<MageObjectReference>> playerDamagedByCreature = new HashMap<>();

    public InfernoTrapWatcher() {
        super(InfernoTrapWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public InfernoTrapWatcher(final InfernoTrapWatcher watcher) {
        super(watcher);
        playerDamagedByCreature.putAll(watcher.playerDamagedByCreature);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && event.getTargetId().equals(controllerId)) {
            Permanent damageBy = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (damageBy != null && damageBy.isCreature()) {
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

    @Override
    public InfernoTrapWatcher copy() {
        return new InfernoTrapWatcher(this);
    }
}
