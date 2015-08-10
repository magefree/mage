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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class Triskelavus extends CardImpl {

    public Triskelavus(UUID ownerId) {
        super(ownerId, 266, "Triskelavus", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Construct");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Triskelavus enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));
        
        // {1}, Remove a +1/+1 counter from Triskelavus: Put a 1/1 colorless Triskelavite artifact creature token with flying onto the battlefield. It has "Sacrifice this creature: This creature deals 1 damage to target creature or player."
         this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TriskelaviteToken()), new RemoveCountersSourceCost(CounterType.P1P1.createInstance())));
    }

    public Triskelavus(final Triskelavus card) {
        super(card);
    }

    @Override
    public Triskelavus copy() {
        return new Triskelavus(this);
    }
}

class TriskelaviteToken extends Token {

    public TriskelaviteToken() {
        super("Triskelavite", "colorless Triskelavite artifact creature token with flying onto the battlefield. It has \"Sacrifice this creature: This creature deals 1 damage to target creature or player.\"");
        this.setOriginalExpansionSetCode("TSP");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Triskelavite");
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new SacrificeSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        
        addAbility(FlyingAbility.getInstance());
    }
}