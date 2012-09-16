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
package mage.sets.urzaslegacy;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetObject;

/**
 *
 * @author Plopman
 */
public class Intervene extends CardImpl<Intervene> {

    public Intervene(UUID ownerId) {
        super(ownerId, 33, "Intervene", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "ULG";

        this.color.setBlue(true);

        // Counter target spell that targets a creature.
        this.getSpellAbility().addTarget(new InterveneTargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    public Intervene(final Intervene card) {
        super(card);
    }

    @Override
    public Intervene copy() {
        return new Intervene(this);
    }
    
    private class InterveneTargetSpell extends TargetObject<InterveneTargetSpell> {


        public InterveneTargetSpell() {
            super(1, Constants.Zone.STACK);
            this.targetName = "spell that targets a creature";
        }

        public InterveneTargetSpell(final InterveneTargetSpell target) {
                super(target); 
        }

        @Override
        public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
                return canChoose(sourceControllerId, game);
        }

        @Override
        public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
                return possibleTargets(sourceControllerId, game);
        }

        @Override
        public boolean canTarget(UUID id, Ability source, Game game) {
                if (super.canTarget(id, source, game)) {
                    if (targetsCreature(id, game)) {
                        return true;
                    }
                }
                return false;
        }

        @Override
        public boolean canChoose(UUID sourceControllerId, Game game) {
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell) {
                    if (targetsCreature(stackObject.getId(), game)) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
            Set<UUID> possibleTargets = new HashSet<UUID>();
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell) {
                    if (targetsCreature(stackObject.getId(), game)) {
                        possibleTargets.add(stackObject.getId());
                    }
                }
            }
            return possibleTargets;
        }


        private boolean targetsCreature(UUID id, Game game) {
            StackObject spell = game.getStack().getStackObject(id);
            if (spell != null) {
                Ability ability = spell.getStackAbility();
                if (ability != null && !ability.getTargets().isEmpty()) {
                    for (Target target : ability.getTargets()) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(targetId);
                            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public InterveneTargetSpell copy() {
                return new InterveneTargetSpell(this);
        }

        @Override
        public Filter getFilter() {
            return new FilterSpell();
        }
    }
}


