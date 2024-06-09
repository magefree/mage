
package mage.filter;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class FilterAbility extends FilterImpl<Ability> {

    public FilterAbility() {
        super("");
    }

    public FilterAbility(String name) {
        super(name);
    }

    protected FilterAbility(final FilterAbility filter) {
        super(filter);
    }

    @Override
    public FilterAbility copy() {
        return new FilterAbility(this);
    }

    public static Predicate<Ability> zone(Zone zone) {
        return new AbilityZonePredicate(zone);
    }

    public static Predicate<Ability> type(AbilityType type) {
        return new AbilityTypePredicate(type);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Ability;
    }

    private static final class AbilityZonePredicate implements Predicate<Ability> {

        private final Zone zone;

        public AbilityZonePredicate(Zone zone) {
            this.zone = zone;
        }

        @Override
        public boolean apply(Ability input, Game game) {
            return input.getZone().match(zone);
        }

        @Override
        public String toString() {
            return "Zone(" + zone.toString() + ')';
        }
    }

    private static final class AbilityTypePredicate implements Predicate<Ability> {

        private final AbilityType type;

        public AbilityTypePredicate(AbilityType type) {
            this.type = type;
        }

        @Override
        public boolean apply(Ability input, Game game) {
            return input.getAbilityType() == type;
        }

        @Override
        public String toString() {
            return "AbilityType(" + type + ')';
        }
    }
}
