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
package mage.sets.mastersedition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;

/**
 *
 * @author Eirkei
 */
public class MartonStromgald extends CardImpl {

    private static final FilterAttackingCreature attackingFilter = new FilterAttackingCreature(" for each attacking creature other than {this}");
    private static final FilterBlockingCreature blockingFilter = new FilterBlockingCreature(" for each blocking creature other than {this}");
    
    static {
        attackingFilter.add(new AnotherPredicate());
        blockingFilter.add(new AnotherPredicate());
    }
        
    public MartonStromgald(UUID ownerId) {
        super(ownerId, 104, "Marton Stromgald", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "MED";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever M&aacute;rton Stromgald attacks, other attacking creatures get +1/+1 until end of turn for each attacking creature other than M&aacute;rton Stromgald.
        PermanentsOnBattlefieldCount attackingValue = new PermanentsOnBattlefieldCount(attackingFilter);
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(attackingValue, attackingValue, Duration.EndOfTurn, new FilterAttackingCreature(), true), false));
        
        // Whenever M&aacute;rton Stromgald blocks, other blocking creatures get +1/+1 until end of turn for each blocking creature other than M&aacute;rton Stromgald.
        PermanentsOnBattlefieldCount blockingValue = new PermanentsOnBattlefieldCount(blockingFilter);
        this.addAbility(new BlocksTriggeredAbility(new BoostAllEffect(blockingValue, blockingValue, Duration.EndOfTurn, new FilterBlockingCreature(), true), false));
        
    }

    public MartonStromgald(final MartonStromgald card) {
        super(card);
    }

    @Override
    public MartonStromgald copy() {
        return new MartonStromgald(this);
    }
}
