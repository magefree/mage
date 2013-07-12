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
package mage.sets.magic2014;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetObject;

/**
 *
 * @author Plopman
 */
public class StrionicResonator extends CardImpl<StrionicResonator> {

    
    
    public StrionicResonator(UUID ownerId) {
        super(ownerId, 224, "Strionic Resonator", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "M14";

        // {2}, {T}: Copy target triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StrionicResonatorEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetTriggeredAbility());
        this.addAbility(ability);
    }

    public StrionicResonator(final StrionicResonator card) {
        super(card);
    }

    @Override
    public StrionicResonator copy() {
        return new StrionicResonator(this);
    }
}

class StrionicResonatorEffect extends OneShotEffect<StrionicResonatorEffect> {

    public StrionicResonatorEffect() {
        super(Outcome.Copy);
    }

    public StrionicResonatorEffect(final StrionicResonatorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility)game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if(stackAbility != null){
            Ability ability = (Ability) stackAbility.getStackAbility();
            Player controller = game.getPlayer(source.getControllerId());
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (ability != null && controller != null && sourcePermanent != null) {
                Ability newAbility = ability.copy();
                newAbility.newId();
                game.getStack().push(new StackAbility(newAbility, source.getControllerId()));
                if (newAbility.getTargets().size() > 0) {
                    if (controller.chooseUse(newAbility.getEffects().get(0).getOutcome(), "Choose new targets?", game)) {
                        newAbility.getTargets().clearChosen();
                        if (newAbility.getTargets().chooseTargets(newAbility.getEffects().get(0).getOutcome(), source.getControllerId(), newAbility, game) == false) {
                            return false;
                        }
                    }
                }
                game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(controller.getName()).append(" copied activated ability").toString());
                return true;
            }
        }
        return false;
        
    }

    @Override
    public StrionicResonatorEffect copy() {
        return new StrionicResonatorEffect(this);
    }

   @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Copy target ").append(mode.getTargets().get(0).getTargetName()).append(". You may choose new targets for the copy");
        return sb.toString();
    }
}

class TargetTriggeredAbility extends TargetObject<TargetTriggeredAbility> {

    public TargetTriggeredAbility() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = "target triggered ability";
    }

    public TargetTriggeredAbility(final TargetTriggeredAbility target) {
        super(target);
    }


    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (source != null && source.getId().equals(id)) {
            return false;
        }

        StackObject stackObject = game.getStack().getStackObject(id);
        if (stackObject.getStackAbility() != null && stackObject.getStackAbility() instanceof TriggeredAbility) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        for (StackObject stackObject :  game.getStack()) {
            if (stackObject.getStackAbility() != null && stackObject.getStackAbility() instanceof TriggeredAbility && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getStackAbility().getControllerId())) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<UUID>();
        for (StackObject stackObject :  game.getStack()) {
            if (stackObject.getStackAbility() != null && stackObject.getStackAbility() instanceof TriggeredAbility && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getStackAbility().getControllerId())) {
                possibleTargets.add(stackObject.getStackAbility().getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetTriggeredAbility copy() {
        return new TargetTriggeredAbility(this);
    }

    @Override
    public Filter getFilter() {
        return new FilterAbility();
    }

}
