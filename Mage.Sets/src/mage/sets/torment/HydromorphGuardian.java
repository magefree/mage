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
package mage.sets.torment;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Rarity;
import mage.constants.Zone;
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
 * @author TaVSt
 */
public class HydromorphGuardian extends CardImpl {

    private static FilterSpell filter = new FilterSpell("spell that targets a creature you control");

    public HydromorphGuardian(UUID ownerId) {
        super(ownerId, 39, "Hydromorph Guardian", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Elemental");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}, Sacrifice Hydromorph Guardian: Counter target spell that targets one or more creatures you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ColoredManaCost(ColoredManaSymbol.U));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new CustomTargetSpell(filter));
        this.addAbility(ability);
    }

    public HydromorphGuardian(final HydromorphGuardian card) {
        super(card);
    }

    @Override
    public HydromorphGuardian copy() {
        return new HydromorphGuardian(this);
    }
    
    private class CustomTargetSpell extends TargetObject {

        protected FilterSpell filter;

        public CustomTargetSpell() {
            this(1, 1, new FilterSpell());
        }

        public CustomTargetSpell(FilterSpell filter) {
            this(1, 1, filter);
        }

        public CustomTargetSpell(int numTargets, FilterSpell filter) {
            this(numTargets, numTargets, filter);
        }

        public CustomTargetSpell(int minNumTargets, int maxNumTargets, FilterSpell filter) {
            this.minNumberOfTargets = minNumTargets;
            this.maxNumberOfTargets = maxNumTargets;
            this.zone = Zone.STACK;
            this.filter = filter;
            this.targetName = filter.getMessage();
        }

        public CustomTargetSpell(final CustomTargetSpell target) {
            super(target);
            this.filter = target.filter.copy();
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
                if (targetsMyCreature(id, source.getControllerId(), game)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canChoose(UUID sourceControllerId, Game game) {
            int count = 0;
            for (StackObject stackObject : game.getStack()) {
                if (stackObject instanceof Spell && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getControllerId()) && filter.match((Spell) stackObject, game)) {
                    if (targetsMyCreature(stackObject.getId(), sourceControllerId, game)) {
                        count++;
                        if (count >= this.minNumberOfTargets)
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
                if (stackObject instanceof Spell && game.getPlayer(sourceControllerId).getInRange().contains(stackObject.getControllerId()) && filter.match((Spell) stackObject, game)) {
                    if (targetsMyCreature(stackObject.getId(), sourceControllerId, game)) {
                        possibleTargets.add(stackObject.getId());
                    }
                }
            }
            return possibleTargets;
        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        private boolean targetsMyCreature(UUID id, UUID controllerId, Game game) {
            StackObject spell = game.getStack().getStackObject(id);
            if (spell != null) {
                Ability ability = spell.getStackAbility();
                for (Target target : ability.getTargets()) {
                    for (UUID permanentId : target.getTargets()) {
                        Permanent permanent = game.getPermanent(permanentId);
                        if (permanent != null && permanent.getControllerId().equals(controllerId) && permanent.getCardType().contains(CardType.CREATURE)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public CustomTargetSpell copy() {
            return new CustomTargetSpell(this);
        }
    }
}
