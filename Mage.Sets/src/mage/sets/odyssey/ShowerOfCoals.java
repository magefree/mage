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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author cbt33, LevelX2 (Kirtar's Wrath)
 */
public class ShowerOfCoals extends CardImpl<ShowerOfCoals> {

    public ShowerOfCoals(UUID ownerId) {
        super(ownerId, 221, "Shower of Coals", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");
        this.expansionSetCode = "ODY";

        this.color.setRed(true);

        // Shower of Coals deals 2 damage to each of up to three target creatures and/or players.
        // Threshold - Shower of Coals deals 4 damage to each of those creatures and/or players instead if seven or more cards are in your graveyard.
        Effect effect = new ConditionalOneShotEffect(new DamageTargetEffect(4), 
                                                     new DamageTargetEffect(2), 
                                                     new CardsInControllerGraveCondition(7),
                                                     "{this} deals 2 damage to each of up to three target creatures and/or players.<br/><br/><i>Threshold<i/> - {this} deals 4 damage to each of those creatures and/or players instead if seven or more cards are in your graveyard.");
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer(0,3));
        this.getSpellAbility().addEffect(effect);
        
        
    }

    public ShowerOfCoals(final ShowerOfCoals card) {
        super(card);
    }

    @Override
    public ShowerOfCoals copy() {
        return new ShowerOfCoals(this);
    }
}
