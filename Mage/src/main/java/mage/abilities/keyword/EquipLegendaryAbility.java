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
package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 * @author Rystan
 */
public class EquipLegendaryAbility extends ActivatedAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("legendary creature you control");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public EquipLegendaryAbility(Outcome outcome, Cost cost) {
        this(outcome, cost, new TargetControlledCreaturePermanent(filter));
    }

    public EquipLegendaryAbility(Outcome outcome, Cost cost, Target target) {
        super(Zone.BATTLEFIELD, new AttachEffect(outcome, "Equip"), cost);
        this.addTarget(target);
        this.timing = TimingRule.SORCERY;
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.hasSubtype(SubType.EQUIPMENT, game)) {
                return true;
            }
        }
        return false;
    }

    public EquipLegendaryAbility(final EquipLegendaryAbility ability) {
        super(ability);
    }

    @Override
    public EquipLegendaryAbility copy() {
        return new EquipLegendaryAbility(this);
    }

    @Override
    public String getRule() {
        return "Equip legendary creature " + costs.getText() + 
                manaCosts.getText() + " (" + manaCosts.getText() + 
                ": <i>Attach to target legendary creature you control. Equip only as a sorcery.)</i>";
    }

}
