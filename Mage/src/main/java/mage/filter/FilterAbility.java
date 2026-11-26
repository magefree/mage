
package mage.filter;

import mage.abilities.Ability;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class FilterAbility extends FilterImpl<Ability> {

    protected final List<ObjectSourcePlayerPredicate<Ability>> extraPredicates = new ArrayList<>();

    public FilterAbility() {
        super("");
    }

    public FilterAbility(String name) {
        super(name);
    }

    protected FilterAbility(final FilterAbility filter) {
        super(filter);
        this.extraPredicates.addAll(filter.extraPredicates);
    }

    public FilterAbility add(ObjectSourcePlayerPredicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }

        // verify check
        Predicates.makeSurePredicateCompatibleWithFilter(predicate, Ability.class);

        extraPredicates.add(predicate);
        return this;
    }

    public boolean match(Ability ability, UUID sourceControllerId, Ability source, Game game) {
        if (!this.match(ability, game)) {
            return false;
        }
        ObjectSourcePlayer<Ability> osp = new ObjectSourcePlayer<>(ability, sourceControllerId, source);
        return extraPredicates.stream().allMatch(p -> p.apply(osp, game));
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

    @Override
    public List<Predicate> getExtraPredicates() {
        return new ArrayList<>(extraPredicates);
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
