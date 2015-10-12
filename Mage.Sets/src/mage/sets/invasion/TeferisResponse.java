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
package mage.sets.invasion;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetObject;
import mage.target.Targets;

/**
 *
 * @author AlumiuN
 */
public class TeferisResponse extends CardImpl {

    public TeferisResponse(UUID ownerId) {
        super(ownerId, 78, "Teferi's Response", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "INV";

        // Counter target spell or ability an opponent controls that targets a land you control. If a permanent's ability is countered this way, destroy that permanent.
        this.getSpellAbility().addEffect(new TeferisResponseEffect());
        this.getSpellAbility().addTarget(new TargetStackObjectTargetingControlledLand());
        
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    public TeferisResponse(final TeferisResponse card) {
        super(card);
    }

    @Override
    public TeferisResponse copy() {
        return new TeferisResponse(this);
    }
}

class TargetStackObjectTargetingControlledLand extends TargetObject {

    public TargetStackObjectTargetingControlledLand() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = "spell or ability an opponent controls that targets a land you control";
    }

    public TargetStackObjectTargetingControlledLand(final TargetStackObjectTargetingControlledLand target) {
        super(target);
    }

    @Override
    public Filter getFilter() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(id);
        if ((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) {
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
        for (StackObject stackObject : game.getStack()) {
            if (((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) && stackObject.getControllerId() != sourceControllerId) {
                Targets objectTargets = stackObject.getStackAbility().getTargets();
                if(!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent != null && targetedPermanent.getCardType().contains(CardType.LAND) && targetedPermanent.getControllerId().equals(sourceControllerId)) {
                                return true;
                            }
                        }
                    }                    
                }
            }       
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId,
            Game game) {
        return possibleTargets(sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        for (StackObject stackObject : game.getStack()) {
            if (((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) && stackObject.getControllerId() != sourceControllerId) {
                Targets objectTargets = stackObject.getStackAbility().getTargets();
                if(!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent != null && targetedPermanent.getCardType().contains(CardType.LAND) && targetedPermanent.getControllerId().equals(sourceControllerId)) {
                                possibleTargets.add(stackObject.getId());
                            }
                        }
                    }                    
                }
            }       
        }        
        return possibleTargets;
    }

    @Override
    public TargetStackObjectTargetingControlledLand copy() {
        return new TargetStackObjectTargetingControlledLand(this);
    }

}

class TeferisResponseEffect extends OneShotEffect {
    
    public TeferisResponseEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell or ability an opponent controls that targets a land you control. If a permanent's ability is countered this way, destroy that permanent";
    }
        
    public TeferisResponseEffect(final TeferisResponseEffect effect) {
        super(effect);
    }

    @Override
    public TeferisResponseEffect copy() {
        return new TeferisResponseEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (targetId != null && game.getStack().counter(targetId, source.getSourceId(), game)) {
            UUID permanentId = stackObject.getSourceId();
            if (permanentId != null) {
                Permanent usedPermanent = game.getPermanent(permanentId);
                if (usedPermanent != null) {
                    usedPermanent.destroy(source.getSourceId(), game, false);
                }
            }
            return true;
        }
        
        return false;
    }
}