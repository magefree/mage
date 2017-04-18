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
package mage.cards.t;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class TimeToReflect extends CardImpl {

    public TimeToReflect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature that blocked or was blocked by a Zombie this turn.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterCreaturePermanent("creature that blocked or was blocked by a Zombie this turn.")));
        this.getSpellAbility().addWatcher(new BlockedOrWasBlockedByAZombieWatcher());

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            List<PermanentIdPredicate> creaturesThatBlockedOrWereBlockedByAZombie = new ArrayList<>();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that blocked or was blocked by a Zombie this turn.").copy();
            BlockedOrWasBlockedByAZombieWatcher watcher = (BlockedOrWasBlockedByAZombieWatcher) game.getState().getWatchers().get("BlockedOrWasBlockedByAZombieWatcher");
            if (watcher != null) {
                for (MageObjectReference mor : watcher.getBlockedThisTurnCreatures()) {
                    Permanent permanent = mor.getPermanent(game);
                    if (permanent != null) {
                        creaturesThatBlockedOrWereBlockedByAZombie.add(new PermanentIdPredicate(permanent.getId()));
                    }
                }
            }
            filter.add(Predicates.or(creaturesThatBlockedOrWereBlockedByAZombie));
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }

    public TimeToReflect(final TimeToReflect card) {
        super(card);
    }

    @Override
    public TimeToReflect copy() {
        return new TimeToReflect(this);
    }
}

class BlockedOrWasBlockedByAZombieWatcher extends Watcher {

    private final Set<MageObjectReference> blockedOrWasBlockedByAZombieWatcher;

    public BlockedOrWasBlockedByAZombieWatcher() {
        super("BlockedOrWasBlockedByAZombieWatcher", WatcherScope.GAME);
        blockedOrWasBlockedByAZombieWatcher = new HashSet<>();
    }

    public BlockedOrWasBlockedByAZombieWatcher(final BlockedOrWasBlockedByAZombieWatcher watcher) {
        super(watcher);
        blockedOrWasBlockedByAZombieWatcher = new HashSet<>(watcher.blockedOrWasBlockedByAZombieWatcher);
    }

    @Override
    public BlockedOrWasBlockedByAZombieWatcher copy() {
        return new BlockedOrWasBlockedByAZombieWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            if (game.getPermanent(event.getTargetId()).getSubtype(game).contains("Zombie")) {
                this.blockedOrWasBlockedByAZombieWatcher.add(new MageObjectReference(event.getSourceId(), game));
            }
            if (game.getPermanent(event.getSourceId()).getSubtype(game).contains("Zombie")) {
                this.blockedOrWasBlockedByAZombieWatcher.add(new MageObjectReference(event.getTargetId(), game));
            }
        }
    }

    public Set<MageObjectReference> getBlockedThisTurnCreatures() {
        return this.blockedOrWasBlockedByAZombieWatcher;
    }

    @Override
    public void reset() {
        super.reset();
        blockedOrWasBlockedByAZombieWatcher.clear();
    }

}
