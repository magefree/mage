package mage.constants;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 * Created by IGOUDT on 26-3-2017.
 */
public enum SuperType {

    BASIC("Basic"),
    ELITE("Elite"),
    LEGENDARY("Legendary"),
    ONGOING("Ongoing"),
    SNOW("Snow"),
    WORLD("World");

    public static class SuperTypePredicate implements Predicate<MageObject> {

        private final SuperType supertype;

        private SuperTypePredicate(SuperType supertype) {
            this.supertype = supertype;
        }

        @Override
        public boolean apply(MageObject input, Game game) {
            return input.getSuperType(game).contains(supertype);
        }

        @Override
        public String toString() {
            return "Supertype(" + supertype + ')';
        }
    }

    private final String text;
    private final SuperTypePredicate predicate;

    SuperType(String text) {
        this.text = text;
        this.predicate = new SuperTypePredicate(this);
    }

    @Override
    public String toString() {
        return text;
    }

    public SuperTypePredicate getPredicate() {
        return predicate;
    }
}
