/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.abilityword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */

public class GrandeurAbility extends ActivatedAbilityImpl {
    
    protected final String cardName;

    public GrandeurAbility(Effect effect, String cardName) {
        super(Zone.BATTLEFIELD, effect);
        this.cardName = cardName;
        
        FilterCard filter = new FilterCard("another card named " + cardName);
        filter.add(new NamePredicate(cardName));
        filter.add(new AnotherCardPredicate());
        this.addCost(new DiscardTargetCost(new TargetCardInHand(filter)));
    }

    public GrandeurAbility(final GrandeurAbility ability) {
        super(ability);
        this.cardName = ability.cardName;
    }

    @Override
    public GrandeurAbility copy() {
        return new GrandeurAbility(this);
    }
    
    @Override
    public String getRule() {
        return new StringBuilder("<i>Grandeur</i> — ").append(super.getRule()).toString();
    }
}
