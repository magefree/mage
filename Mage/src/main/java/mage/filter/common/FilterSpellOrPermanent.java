package mage.filter.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.MultiFilterImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author LevelX
 */
public class FilterSpellOrPermanent extends MultiFilterImpl<MageObject> {

    public FilterSpellOrPermanent() {
        this("spell or permanent");
    }

    public FilterSpellOrPermanent(String name) {
        super(name, new FilterPermanent(), new FilterSpell());
    }

    protected FilterSpellOrPermanent(final FilterSpellOrPermanent filter) {
        super(filter);
    }


    @Override
    public boolean match(MageObject object, Game game) {
        // We can not rely on super.match, since Permanent extend Card
        // and currently FilterSpell accepts to filter Cards
        if (object instanceof Permanent) {
            return getPermanentFilter().match((Permanent) object, game);
        } else if (object instanceof Spell) {
            return getSpellFilter().match((Spell) object, game);
        } else {
            return false;
        }
    }

    @Override
    public boolean match(MageObject object, UUID sourceControllerId, Ability source, Game game) {
        // We can not rely on super.match, since Permanent extend Card
        // and currently FilterSpell accepts to filter Cards
        if (object instanceof Permanent) {
            return getPermanentFilter().match((Permanent) object, sourceControllerId, source, game);
        } else if (object instanceof Spell) {
            return getSpellFilter().match((Spell) object, sourceControllerId, source, game);
        } else {
            return false;
        }
    }

    @Override
    public FilterSpellOrPermanent copy() {
        return new FilterSpellOrPermanent(this);
    }

    public FilterPermanent getPermanentFilter() {
        return (FilterPermanent) this.innerFilters.get(0);
    }

    public FilterSpell getSpellFilter() {
        return (FilterSpell) this.innerFilters.get(1);
    }
}
