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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.EquippedMatchesFilterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.RegenerateEquippedEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class RingOfXathrid extends CardImpl<RingOfXathrid> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    
    static {
        filter.getColor().setBlack(true);
        filter.setUseColor(true);
    }

    public RingOfXathrid(UUID ownerId) {
        super(ownerId, 215, "Ring of Xathrid", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "M13";
        this.subtype.add("Equipment");

        // {2}: Regenerate equipped creature.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RegenerateEquippedEffect(), new GenericManaCost(2)));
        
        // At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's black.
        TriggeredAbility triggeredAbility = new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new AddPlusOneCountersAttachedEffect(1), Constants.TargetController.YOU, false);
        ConditionalTriggeredAbility ability = new ConditionalTriggeredAbility(triggeredAbility, new EquippedMatchesFilterCondition(filter), "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's black");
        this.addAbility(ability);
        
        // Equip {1}
        this.addAbility(new EquipAbility(Constants.Outcome.BoostCreature, new GenericManaCost(1)));
    }

    public RingOfXathrid(final RingOfXathrid card) {
        super(card);
    }

    @Override
    public RingOfXathrid copy() {
        return new RingOfXathrid(this);
    }
}
