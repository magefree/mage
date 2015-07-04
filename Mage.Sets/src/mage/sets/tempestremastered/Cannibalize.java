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
package mage.sets.tempestremastered;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Cannibalize extends CardImpl {

    public Cannibalize(UUID ownerId) {
        super(ownerId, 83, "Cannibalize", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "TPR";

        // Choose two target creatures controlled by the same player. Exile one of the creatures and put two +1/+1 counters on the other.
        this.getSpellAbility().addEffect(new CannibalizeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2,2,new FilterCreaturePermanent(),false));
    }

    public Cannibalize(final Cannibalize card) {
        super(card);
    }

    @Override
    public Cannibalize copy() {
        return new Cannibalize(this);
    }
}



class CannibalizeEffect extends OneShotEffect {
    
    public CannibalizeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose two target creatures controlled by the same player. Exile one of the creatures and put two +1/+1 counters on the other";
    }
    
    public CannibalizeEffect(final CannibalizeEffect effect) {
        super(effect);
    }
    
    @Override
    public CannibalizeEffect copy() {
        return new CannibalizeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            boolean exileDone = false;
            int count = 0;
            for(UUID targetId: getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);                
                if (creature != null) {
                    if ((count == 0 && controller.chooseUse(Outcome.Exile, "Exile " + creature.getLogName() +"?", source, game)) 
                            || (count == 1 && !exileDone)) {
                        controller.moveCardToExileWithInfo(creature, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
                        exileDone = true;
                    } else {
                        creature.addCounters(CounterType.P1P1.createInstance(2), game);
                        game.informPlayers("Added two +1/+1 counters on " + creature.getLogName());
                    }
                    count++;
                }
            }
            return true;
        }
        return false;
    }
}

class TargetCreaturePermanentSameController extends TargetCreaturePermanent {

    public TargetCreaturePermanentSameController(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
        this.targetName = filter.getMessage();
    }

    public TargetCreaturePermanentSameController(final TargetCreaturePermanentSameController target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = this.getFirstTarget();
        if (firstTarget != null) {
            Permanent permanent = game.getPermanent(firstTarget);
            Permanent targetPermanent = game.getPermanent(id);
            if (permanent == null || targetPermanent == null
                    || !permanent.getControllerId().equals(targetPermanent.getOwnerId())) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }

    @Override
    public TargetCreaturePermanentSameController copy() {
        return new TargetCreaturePermanentSameController(this);
    }
}