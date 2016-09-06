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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class BurningTreeBloodscale extends CardImpl {

    public BurningTreeBloodscale(UUID ownerId) {
        super(ownerId, 104, "Burning-Tree Bloodscale", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Viashino");
        this.subtype.add("Berserker");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bloodthirst 1
        this.addAbility(new BloodthirstAbility(1));
        
        // {2}{R}: Target creature can't block Burning-Tree Bloodscale this turn.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn), 
        		new ManaCostsImpl("{2}{R}"));
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);
        
        // {2}{G}: Target creature blocks Burning-Tree Bloodscale this turn if able.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn), 
        		new ManaCostsImpl("{2}{G}"));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    public BurningTreeBloodscale(final BurningTreeBloodscale card) {
        super(card);
    }

    @Override
    public BurningTreeBloodscale copy() {
        return new BurningTreeBloodscale(this);
    }
}
