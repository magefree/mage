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

import java.util.UUID;

import mage.MageObject;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX
 */
public class FilterSpellOrPermanent extends FilterImpl<MageObject> implements FilterInPlay<MageObject> {

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
    public boolean checkObjectClass(Object object) {
        return object instanceof MageObject;
    }

    @Override
    public boolean match(MageObject o, Game game) {
        if (o instanceof Spell) {
            return spellFilter.match((Spell) o, game);
        } else if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public boolean match(MageObject o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Spell) {
            return spellFilter.match((Spell) o, sourceId, playerId, game);
        } else if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    public final void add(ObjectPlayerPredicate<? extends ObjectPlayer> predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        spellFilter.add(predicate);
        permanentFilter.add(predicate);
    }

    public FilterPermanent getPermanentFilter() {
        return this.permanentFilter;
    }

    public FilterSpell getSpellFilter() {
        return this.spellFilter;
    }

    public void setPermanentFilter(FilterPermanent permanentFilter) {
        this.permanentFilter = permanentFilter;
    }

    public void setSpellFilter(FilterSpell spellFilter) {
        this.spellFilter = spellFilter;
    }

    @Override
    public FilterSpellOrPermanent copy() {
        return new FilterSpellOrPermanent(this);
    }

}
