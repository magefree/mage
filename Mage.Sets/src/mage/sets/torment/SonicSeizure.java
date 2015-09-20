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
package mage.sets.torment;

import java.util.UUID;
import mage.MageObject;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.target.common.TargetCreatureOrPlayer;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author tomd1990
 */
public class SonicSeizure extends CardImpl {

    public SonicSeizure(UUID ownerId) {
        super(ownerId, 115, "Sonic Seizure", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "TOR";

        // As an additional cost to cast Sonic Seizure, discard a card at random.
        this.getSpellAbility().addCost(new DiscardCardCost(true));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        // Sonic Seizure deals 3 damage to target creature or player.        
        this.getSpellAbility().addEffect(new SonicSeizureEffect());
    }

    public SonicSeizure(final SonicSeizure card) {
        super(card);
    }

    @Override
    public SonicSeizure copy() {
        return new SonicSeizure(this);
    }
}


class SonicSeizureEffect extends OneShotEffect {
    
    public SonicSeizureEffect() {
        super(Outcome.Damage);
        staticText = "{source} deals 3 damage to target creature or player.";
    }
        
    public SonicSeizureEffect(final SonicSeizureEffect effect) {
        super(effect);
    }
    
    @Override
    public SonicSeizureEffect copy() {
        return new SonicSeizureEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if(sourceObject != null) {
            DiscardCardCost cost = (DiscardCardCost) source.getCosts().get(0);
            if (cost != null) {
                DamageTargetEffect dmg = new DamageTargetEffect(3);
                dmg.apply(game, source);
            }
            return true;
        }
        return false;
    }
    
    
}