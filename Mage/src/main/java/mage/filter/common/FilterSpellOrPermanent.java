package mage.filter.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
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
    public boolean match(MageObject o, UUID playerId, Ability source, Game game) {
        if (o instanceof Spell) {
            return spellFilter.match((Spell) o, playerId, source, game);
        } else if (o instanceof Permanent) {
            return permanentFilter.match((Permanent) o, playerId, source, game);
        }
        return false;
    }

    @Override
    public void setLockedFilter(boolean lockedFilter) {
        super.setLockedFilter(lockedFilter);
        spellFilter.setLockedFilter(lockedFilter);
        permanentFilter.setLockedFilter(lockedFilter);
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
