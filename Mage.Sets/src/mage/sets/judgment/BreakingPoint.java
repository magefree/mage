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
package mage.sets.judgment;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author ilcartographer
 */
public class BreakingPoint extends CardImpl {

    public BreakingPoint(UUID ownerId) {
        super(ownerId, 81, "Breaking Point", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");
        this.expansionSetCode = "JUD";

        // Any player may have Breaking Point deal 6 damage to him or her. If no one does, destroy all creatures. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new BreakingPointDestroyEffect());
    }

    public BreakingPoint(final BreakingPoint card) {
        super(card);
    }

    @Override
    public BreakingPoint copy() {
        return new BreakingPoint(this);
    }
}

class BreakingPointDestroyEffect extends OneShotEffect {
    
    public BreakingPointDestroyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Any player may have {this} deal 6 damage to him or her. If no one does, destroy all creatures. Creatures destroyed this way can't be regenerated.";
    }
    
    public BreakingPointDestroyEffect(final BreakingPointDestroyEffect effect) {
        super(effect);
    }
    
    @Override
    public BreakingPointDestroyEffect copy() {
        return new BreakingPointDestroyEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        
        StackObject spell = null;
        for(StackObject object : game.getStack()){
            if(object instanceof Spell && object.getSourceId().equals(source.getSourceId())){
                spell = object;
            }
        }
        if(spell != null){
            boolean destroyCreatures = true;
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)){
                Player player = game.getPlayer(playerId);
                if(player != null && player.chooseUse(Outcome.Detriment, "Have " + spell.getLogName() + " deal 6 damage to you?", game)){
                    destroyCreatures = false;
                    player.damage(6, source.getSourceId(), game, false, true);
                    game.informPlayers(player.getLogName() + " has " + spell.getName() + " deal 6 to him or her");
                }
            }
            if (destroyCreatures) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent("creatures"), source.getControllerId(), source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, true);
                }
            }
            return destroyCreatures;
        }
        return false;
    }
}
