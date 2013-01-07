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
package mage.sets.scourge;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterAbility;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetObject;


/**
 *
 * @author Plopman
 */
public class Stifle extends CardImpl<Stifle> {

    public Stifle(UUID ownerId) {
        super(ownerId, 52, "Stifle", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "SCG";

        this.color.setBlue(true);

        // Counter target activated or triggered ability.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new StifleTarget());
    }

    public Stifle(final Stifle card) {
        super(card);
    }

    @Override
    public Stifle copy() {
        return new Stifle(this);
    }
}


class StifleTarget extends TargetObject<StifleTarget> {

    public StifleTarget() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Constants.Zone.STACK;
        this.targetName = "target activated or triggered ability";
    }

    public StifleTarget(final StifleTarget target) {
        super(target);
    }


    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (source != null && source.getId().equals(id)) {
            return false;
        }
        
        StackObject stackObject = game.getStack().getStackObject(id);
        if (stackObject.getStackAbility() != null && (stackObject.getStackAbility() instanceof ActivatedAbility || stackObject.getStackAbility() instanceof TriggeredAbility)) {
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
            if (stackObject.getStackAbility() != null && (stackObject.getStackAbility() instanceof ActivatedAbility || stackObject.getStackAbility() instanceof TriggeredAbility) && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getStackAbility().getControllerId())) {
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
            if (stackObject.getStackAbility() != null && (stackObject.getStackAbility() instanceof ActivatedAbility || stackObject.getStackAbility() instanceof TriggeredAbility) && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getStackAbility().getControllerId())) {
                possibleTargets.add(stackObject.getStackAbility().getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public StifleTarget copy() {
        return new StifleTarget(this);
    }

    @Override
    public Filter getFilter() {
        return new FilterAbility();
    }

}
