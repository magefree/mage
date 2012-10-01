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
 
package mage.abilities.costs.common;
 
import java.util.UUID;
import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.costs.CostImpl;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
 
/**
 *
 * @author LevelX2
 */
public class ExileFromStackCost extends CostImpl<ExileFromStackCost> {
 
    public ExileFromStackCost(TargetSpell target) {
        this.addTarget(target);
        this.text = "Exile " + target.getTargetName();
    }
 
    public ExileFromStackCost(ExileFromStackCost cost) {
        super(cost);
    }
 
    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Constants.Outcome.Exile, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            for (UUID targetId: targets.get(0).getTargets()) {
                Spell spellToExile = game.getStack().getSpell(targetId);
                if (spellToExile == null) {
                    return false;
                }
                paid |= spellToExile.moveToExile(null, "", ability.getSourceId(), game);
                if (paid) {
                    game.informPlayers(player.getName() + " exiles " + spellToExile.getName() +" (as costs)");
                }
            }
        }
        return paid;
    }
 
    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }
 
    @Override
    public ExileFromStackCost copy() {
        return new ExileFromStackCost(this);
    }

}
