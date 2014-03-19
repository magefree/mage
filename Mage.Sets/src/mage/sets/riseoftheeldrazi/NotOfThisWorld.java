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
package mage.sets.riseoftheeldrazi;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import static mage.filter.predicate.permanent.ControllerControlsIslandPredicate.filter;
import mage.filter.predicate.permanent.ControllerPredicate;
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
 * @author Rafbill
 */
public class NotOfThisWorld extends CardImpl<NotOfThisWorld> {

    public NotOfThisWorld(UUID ownerId) {
        super(ownerId, 8, "Not of This World", Rarity.UNCOMMON,
                new CardType[]{CardType.INSTANT}, "{7}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Tribal");
        this.subtype.add("Eldrazi");

        // Counter target spell or ability that targets a permanent you control.
        this.getSpellAbility().addTarget(
                new TargetSpellTargetingControlledPermanent());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        // Not of This World costs {7} less to cast if it targets a spell or ability that targets a creature you control with power 7 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new SpellCostReductionSourceEffect(7, NotOfThisWorldCondition.getInstance())));
    }

    public NotOfThisWorld(final NotOfThisWorld card) {
        super(card);
    }

    @Override
    public NotOfThisWorld copy() {
        return new NotOfThisWorld(this);
    }
}

class TargetSpellTargetingControlledPermanent extends TargetObject<TargetSpellTargetingControlledPermanent> {

    public TargetSpellTargetingControlledPermanent() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = filter.getMessage();
    }

    public TargetSpellTargetingControlledPermanent(final TargetSpellTargetingControlledPermanent target) {
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
            if ((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) {
                Targets objectTargets = stackObject.getStackAbility().getTargets();
                if(!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent.getControllerId().equals(sourceControllerId)) {
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
            if ((stackObject instanceof Spell) || (stackObject instanceof StackAbility)) {
                Targets objectTargets = stackObject.getStackAbility().getTargets();
                if(!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent.getControllerId().equals(sourceControllerId)) {
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
    public TargetSpellTargetingControlledPermanent copy() {
        return new TargetSpellTargetingControlledPermanent(this);
    }

}

class NotOfThisWorldCondition implements Condition {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control with power 7 or greater");

    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 6));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    private static final NotOfThisWorldCondition fInstance = new NotOfThisWorldCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell != null && sourceSpell.getStackAbility().getTargets().isChosen()) {
            StackObject objectToCounter = game.getStack().getStackObject(sourceSpell.getStackAbility().getTargets().getFirstTarget());
            if (objectToCounter != null) {
                for (Target target : objectToCounter.getStackAbility().getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                        if (targetedPermanent != null && filter.match(targetedPermanent, sourceSpell.getSourceId(), sourceSpell.getControllerId(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a spell or ability that targets a creature you control with power 7 or greater";
    }

}
