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
package mage.cards.v;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public class VodalianWarMachine extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Merfolk you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public VodalianWarMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Tap an untapped Merfolk you control: Vodalian War Machine can attack this turn as though it didn't have defender.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);
        
        // Tap an untapped Merfolk you control: Vodalian War Machine gets +2/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 1, Duration.EndOfTurn), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true))));
        
        // When Vodalian War Machine dies, destroy all Merfolk tapped this turn to pay for its abilities.
        this.addAbility(new DiesTriggeredAbility(new VodalianWarMachineEffect(), false), new VodalianWarMachineWatcher());
    }

    public VodalianWarMachine(final VodalianWarMachine card) {
        super(card);
    }

    @Override
    public VodalianWarMachine copy() {
        return new VodalianWarMachine(this);
    }
}

class VodalianWarMachineEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Merfolk tapped this turn to pay for its abilities");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public VodalianWarMachineEffect() {
        super(Outcome.Detriment);
        staticText = "destroy all " + filter.getMessage();
    }

    public VodalianWarMachineEffect(final VodalianWarMachineEffect effect) {
        super(effect);
    }

    @Override
    public VodalianWarMachineEffect copy() {
        return new VodalianWarMachineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null) {
            VodalianWarMachineWatcher watcher = (VodalianWarMachineWatcher) game.getState().getWatchers().get(VodalianWarMachineWatcher.class.getSimpleName());
            if (watcher != null && watcher.getTappedMerfolkIds(sourcePermanent.getId()) != null) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                    if (watcher.getTappedMerfolkIds(sourcePermanent.getId()).contains(permanent.getId())) {
                        permanent.destroy(source.getSourceId(), game, false);
                    }
                }
                return true;
            }
        }
        return false;
    }

}

class VodalianWarMachineWatcher extends Watcher {

    public Map<UUID, Set<UUID>> tappedMerfolkIds = new HashMap<>();

    public VodalianWarMachineWatcher() {
        super(VodalianWarMachineWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public VodalianWarMachineWatcher(final VodalianWarMachineWatcher watcher) {
        super(watcher);
        this.tappedMerfolkIds = watcher.tappedMerfolkIds;
    }

    @Override
    public VodalianWarMachineWatcher copy() {
        return new VodalianWarMachineWatcher(this);
    }

    public Set<UUID> getTappedMerfolkIds(UUID sourceId) {
        return tappedMerfolkIds.get(sourceId);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            if (event.getSourceId() != null) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (stackAbility != null) {
                    Ability ability = stackAbility.getStackAbility();
                    if (ability != null) {
                        for (Cost cost : ability.getCosts()) {
                            if (cost instanceof TapTargetCost && cost.isPaid()) {
                                TapTargetCost tapCost = (TapTargetCost) cost;
                                if (tapCost.getTarget().isChosen()) {
                                    Set<UUID> toAdd;
                                    if (tappedMerfolkIds.get(event.getSourceId()) == null) {
                                        toAdd = new HashSet<>();
                                    } else {
                                        toAdd = tappedMerfolkIds.get(event.getSourceId());
                                    }
                                    for (UUID targetId : tapCost.getTarget().getTargets()) {
                                        toAdd.add(targetId);
                                    }
                                    tappedMerfolkIds.put(event.getSourceId(), toAdd);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        tappedMerfolkIds.clear();
    }
}
