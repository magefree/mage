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
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
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
 * @author spjspj
 */
public class SirenStormtamer extends CardImpl {

    public SirenStormtamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add("Siren");
        this.subtype.add("Pirate");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {U}, Sacrifice Siren Stormtamer: Counter target spell or ability that targets you or a creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl("{U}"));
        ability.addTarget(new SirenStormtamerTargetObject());
        ability.addCost(new SacrificeSourceCost());

        this.addAbility(ability);
    }

    public SirenStormtamer(final SirenStormtamer card) {
        super(card);
    }

    @Override
    public SirenStormtamer copy() {
        return new SirenStormtamer(this);
    }
}

class SirenStormtamerTargetObject extends TargetObject {

    public SirenStormtamerTargetObject() {
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
        this.zone = Zone.STACK;
        this.targetName = "spell or ability that targets you or a creature you control";
    }

    public SirenStormtamerTargetObject(final SirenStormtamerTargetObject target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(id);
        return (stackObject instanceof Spell) || (stackObject instanceof StackAbility);
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
                if (!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent != null
                                    && targetedPermanent.getControllerId().equals(sourceControllerId)
                                    && targetedPermanent.isCreature()) {
                                return true;
                            }

                            if (sourceControllerId.equals(targetId)) {
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
                if (!objectTargets.isEmpty()) {
                    for (Target target : objectTargets) {
                        for (UUID targetId : target.getTargets()) {
                            Permanent targetedPermanent = game.getPermanentOrLKIBattlefield(targetId);
                            if (targetedPermanent != null
                                    && targetedPermanent.getControllerId().equals(sourceControllerId)
                                    && targetedPermanent.isCreature()) {
                                possibleTargets.add(stackObject.getId());
                            }

                            if (sourceControllerId.equals(targetId)) {
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
    public SirenStormtamerTargetObject copy() {
        return new SirenStormtamerTargetObject(this);
    }

    @Override
    public Filter getFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
