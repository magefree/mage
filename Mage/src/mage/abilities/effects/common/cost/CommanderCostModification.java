/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common.cost;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.CastCommanderAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author Plopman
 */

//20130711
/*903.10. A player may cast a commander he or she owns from the command zone.
 * Doing so costs that player an additional {2} for each previous time he or she cast that commander from the command zone that game.
 * */
public class CommanderCostModification extends CostModificationEffectImpl<CommanderCostModification> {

    private UUID commander;
    
    public CommanderCostModification(UUID commander) {
        super(Duration.Custom, Outcome.Neutral);
        this.commander = commander;
    }

    public CommanderCostModification(final CommanderCostModification effect) {
        super(effect);
        this.commander = effect.commander;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
                    
        Mana mana = abilityToModify.getManaCostsToPay().getMana();
        
        Integer castCount = (Integer)game.getState().getValue(commander + "_castCount");
        if(castCount == null){
            castCount = 0;
            game.getState().setValue(commander + "_castCount", castCount);
        }
                
        int newCount = mana.getColorless() + 2*castCount;
        mana.setColorless(newCount);
        abilityToModify.getManaCostsToPay().load(mana.toString());
        return true;
        
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof CastCommanderAbility && abilityToModify.getSourceId().equals(commander)) {
            return true;
        }
        return false;
    }

    @Override
    public CommanderCostModification copy() {
        return new CommanderCostModification(this);
    }
}
