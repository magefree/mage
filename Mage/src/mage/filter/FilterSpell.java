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

package mage.filter;

import mage.Constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterSpell<T extends FilterSpell<T>> extends FilterStackObject<FilterSpell<T>> {

    protected Zone fromZone = Zone.ALL;
    protected boolean notFromZone = false;    
    
	public FilterSpell() {
		super("spell");
	}

	public FilterSpell(String name) {
		super(name);
	}

	public FilterSpell(final FilterSpell filter) {
		super(filter);
        for (Object cId: filter.getControllerId()) {
			this.controllerId.add((UUID)cId);
		}
		this.notController = filter.notController;
		this.controller = filter.controller;
	}

	@Override
	public boolean match(StackObject spell, Game game) {
		if (!(spell instanceof Spell))
			return notFilter;

        if (((Spell)spell).getFromZone().match(fromZone) == notFromZone)
            return notFilter;
        
		return super.match(spell, game);
	}

    @Override
	public boolean match(StackObject spell, UUID playerId, Game game) {
		if (!this.match(spell, game))
			return notFilter;
        
        return super.match(spell, playerId, game);
    }
    
	@Override
	public FilterSpell copy() {
		return new FilterSpell(this);
	}

    public void setFromZone(Zone fromZone) {
        this.fromZone = fromZone;
    }
    
	public void setNotFromZone(boolean notFromZone) {
		this.notFromZone = notFromZone;
	}

}
