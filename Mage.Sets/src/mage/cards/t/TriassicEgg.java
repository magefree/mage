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
package mage.sets.legends;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class TriassicEgg extends CardImpl {

    public TriassicEgg(UUID ownerId) {
        super(ownerId, 242, "Triassic Egg", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "LEG";

        // {3}, {tap}: Put a hatchling counter on Triassic Egg.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new AddCountersSourceEffect(CounterType.HATCHLING.createInstance(), true), 
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        // Sacrifice Triassic Egg: Choose one - You may put a creature card from your hand onto the battlefield;
        ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new PutPermanentOnBattlefieldEffect(new FilterCreatureCard("a creature card")),
                new SacrificeSourceCost(),
                new SourceHasCounterCondition(CounterType.HATCHLING, 2, Integer.MAX_VALUE),
                "Sacrifice Triassic Egg: Choose one - You may put a creature card from your hand onto the battlefield; or return target creature card from your graveyard to the battlefield. Activate this ability only if two or more hatchling counters are on {this}.");
        
        // or return target creature card from your graveyard to the battlefield. Activate this ability only if two or more hatchling counters are on Triassic Egg.
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        Target target = new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"));
        mode.getTargets().add(target);
        ability.addMode(mode);
        
        this.addAbility(ability);
    }

    public TriassicEgg(final TriassicEgg card) {
        super(card);
    }

    @Override
    public TriassicEgg copy() {
        return new TriassicEgg(this);
    }
}