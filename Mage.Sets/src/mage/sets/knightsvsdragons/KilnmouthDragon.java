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
package mage.sets.knightsvsdragons;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author FenrisulfrX
 */
public class KilnmouthDragon extends CardImpl {

    public KilnmouthDragon(UUID ownerId) {
        super(ownerId, 59, "Kilnmouth Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.expansionSetCode = "DDG";
        this.subtype.add("Dragon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Amplify 3
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify3));
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {tap}: Kilnmouth Dragon deals damage equal to the number of +1/+1 counters on it to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(new CountersCount(CounterType.P1P1)), new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public KilnmouthDragon(final KilnmouthDragon card) {
        super(card);
    }

    @Override
    public KilnmouthDragon copy() {
        return new KilnmouthDragon(this);
    }
}
