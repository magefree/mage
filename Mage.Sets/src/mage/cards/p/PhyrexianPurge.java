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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyMultiTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 - Derek Monturo
 */
public class PhyrexianPurge extends CardImpl {
    
    public PhyrexianPurge(UUID ownerId, CardSetInfo setInfo) {
        
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");              
        
        // Destroy any number of target creatures.
        // Phyrexian Purge costs 3 life more to cast for each target.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new DestroyMultiTargetEffect());
    }
    
    @Override
    public void adjustCosts(Ability ability, Game game) {
        int numTargets = ability.getTargets().get(0).getTargets().size();
        if (numTargets > 0) {
            ability.getCosts().add(new PayLifeCost(numTargets * 3));
        }
    }
        
    @Override
    public void adjustTargets(Ability ability, Game game) {
       if (ability instanceof SpellAbility) {
           ability.getTargets().clear();
           Player you = game.getPlayer(ownerId);
           int maxTargets = you.getLife() / 3;
           ability.addTarget(new TargetCreaturePermanent(0, maxTargets));
       }
    }
    
    public PhyrexianPurge(final PhyrexianPurge card) {
        super(card);
    }

    @Override
    public PhyrexianPurge copy() {
        return new PhyrexianPurge(this);
    }
}