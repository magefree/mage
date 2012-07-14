/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.filter.common;

import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public class FilterSpellOrPermanent extends FilterImpl<Object> implements FilterInPlay<Object> {

    protected FilterPermanent permanentFilter;
    protected FilterSpell spellFilter;

    public FilterSpellOrPermanent() {
        this("spell or permanent");
    }

    public FilterSpellOrPermanent(String name) {
        super(name);
        permanentFilter = new FilterPermanent();
        spellFilter = new FilterSpell();
    }

    public FilterSpellOrPermanent(final FilterSpellOrPermanent filter) {
        super(filter);
        this.permanentFilter = filter.permanentFilter.copy();
        this.spellFilter = filter.spellFilter.copy();
    }

    @Override
    public boolean match(Object o, Game game) {
        if (o instanceof Spell) {
            return spellFilter.match((Spell) o, game);
        } else if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, game);
        }
        return notFilter;
    }

    @Override
    public boolean match(Object o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Spell) {
            return spellFilter.match((Spell) o, playerId, game);
        } else if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return notFilter;
    }

    public FilterPermanent getPermanentFilter() {
        return this.permanentFilter;
    }

    public FilterSpell getspellFilter() {
        return this.spellFilter;
    }

    @Override
    public FilterSpellOrPermanent copy() {
        return new FilterSpellOrPermanent(this);
    }

}
