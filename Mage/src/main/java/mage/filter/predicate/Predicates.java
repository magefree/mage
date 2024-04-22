package mage.filter.predicate;

import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Static utility methods pertaining to {@code Predicate} instances.
 *
 * <p>All methods returns serializable predicates as long as they're given serializable parameters.</p>
 *
 * @author North
 */
public final class Predicates {

    private Predicates() {
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the given predicate evaluates to {@code false}.
     *
     * @param <T>
     * @param predicate
     * @return
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate<T>(predicate);
    }

    /**
     * Returns a predicate that evaluates to {@code true} if each of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is
     * found. It defensively copies the iterable passed in, so future changes to it won't alter the behavior of this
     * predicate. If {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     *
     * @param <T>
     * @param components
     * @return
     */
    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) {
        return new AndPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if each of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is
     * found. It defensively copies the array passed in, so future changes to it won't alter the behavior of this
     * predicate. If {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     *
     * @param <T>
     * @param components
     * @return
     */
    public static <T> Predicate<T> and(Predicate<? super T>... components) {
        return new AndPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if both of its components evaluate to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is
     * found.
     *
     * @param <T>
     * @param first
     * @param second
     * @return
     */
    public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return new AndPredicate<T>(Predicates.asList(checkNotNull(first), checkNotNull(second)));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if any one of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found.
     * It defensively copies the iterable passed in, so future changes to it won't alter the behavior of this predicate.
     * If {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     *
     * @param <T>
     * @param components
     * @return
     */
    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) {
        return new OrPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if any one of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found.
     * It defensively copies the array passed in, so future changes to it won't alter the behavior of this predicate. If
     * {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     *
     * @param <T>
     * @param components
     * @return
     */
    public static <T> Predicate<T> or(Predicate<? super T>... components) {
        return new OrPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if either of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found.
     *
     * @param <T>
     * @param first
     * @param second
     * @return
     */
    public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second) {
        return new OrPredicate<T>(Predicates.asList(first, second));
    }

    /**
     * @see Predicates#not(Predicate)
     */
    private static class NotPredicate<T> implements Predicate<T> {

        final Predicate<T> predicate;

        NotPredicate(Predicate<T> predicate) {
            this.predicate = checkNotNull(predicate);
        }

        @Override
        public boolean apply(T t, Game game) {
            return !predicate.apply(t, game);
        }

        @Override
        public String toString() {
            return "Not(" + predicate.toString() + ')';
        }

        private static final long serialVersionUID = 0;
    }

    /**
     * @see Predicates#and(Iterable)
     */
    private static class AndPredicate<T> implements Predicate<T> {

        private final List<? extends Predicate<? super T>> components;

        private AndPredicate(List<? extends Predicate<? super T>> components) {
            this.components = components;
        }

        @Override
        public boolean apply(T t, Game game) {
            return components.stream().allMatch(predicate -> predicate.apply(t, game));

        }

        @Override
        public String toString() {
            return "And(" + commaJoin(components) + ')';
        }

        private static final long serialVersionUID = 0;
    }

    /**
     * @see Predicates#or(Iterable)
     */
    private static class OrPredicate<T> implements Predicate<T> {

        private final List<? extends Predicate<? super T>> components;

        private OrPredicate(List<? extends Predicate<? super T>> components) {
            this.components = components;
        }

        @Override
        public boolean apply(T t, Game game) {
            return components.stream().anyMatch(predicate -> predicate.apply(t, game));
        }

        @Override
        public String toString() {
            return "Or(" + commaJoin(components) + ')';
        }

        private static final long serialVersionUID = 0;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) {
        return Arrays.asList(first, second);
    }

    private static <T> List<T> defensiveCopy(T... array) {
        return defensiveCopy(Arrays.asList(array));
    }

    static <T> List<T> defensiveCopy(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T element : iterable) {
            list.add(checkNotNull(element));
        }
        return list;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    private static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    private static String commaJoin(List components) {
        StringBuilder sb = new StringBuilder();
        for (Object component : components) {
            sb.append(component.toString());
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Collect real predicates for searching some data (see dependency effect code)
     */
    public static void collectAllComponents(Predicate predicate, List<Predicate> res) {
        if (predicate instanceof NotPredicate) {
            collectAllComponents(((NotPredicate) predicate).predicate, res);
        } else if (predicate instanceof AndPredicate) {
            collectAllComponents(((AndPredicate) predicate).components, null, res);
        } else if (predicate instanceof OrPredicate) {
            collectAllComponents(((OrPredicate) predicate).components, null, res);
        } else {
            res.add(predicate);
        }
    }

    public static void collectAllComponents(List<Predicate> predicates, List<Predicate> extraPredicates, List<Predicate> res) {
        predicates.forEach(p -> collectAllComponents(p, res));
        if (extraPredicates != null) {
            extraPredicates.forEach(p -> collectAllComponents(p, res));
        }
    }
}
