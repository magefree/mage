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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public class PlanarCollapse extends CardImpl<PlanarCollapse> {

    public PlanarCollapse(UUID ownerId) {
        super(ownerId, 18, "Planar Collapse", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "ULG";

        this.color.setWhite(true);

        // At the beginning of your upkeep, if there are four or more creatures on the battlefield, sacrifice Planar Collapse and destroy all creatures. They can't be regenerated.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), Constants.TargetController.YOU, false);
        ability.addEffect(new DestroyAllEffect(new FilterCreaturePermanent(), true));
        PlanarCollapseCondition contition = new PlanarCollapseCondition();
        this.addAbility(new ConditionalTriggeredAbility(ability, contition, "At the beginning of your upkeep, if there are four or more creatures on the battlefield, sacrifice {this} and destroy all creatures. They can't be regenerated"));
        
    }

    public PlanarCollapse(final PlanarCollapse card) {
        super(card);
    }

    @Override
    public PlanarCollapse copy() {
        return new PlanarCollapse(this);
    }
    
    class PlanarCollapseCondition implements mage.abilities.condition.Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            return game.getBattlefield().count(new FilterCreaturePermanent(), source.getSourceId(), source.getControllerId(), game) >= 4;
        }
    }
}
