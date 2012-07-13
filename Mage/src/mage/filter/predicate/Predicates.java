/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
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
 */
package mage.filter.predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.game.Game;

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
     */
    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate<T>(predicate);
    }

    /**
     * Returns a predicate that evaluates to {@code true} if each of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is
     * found. It defensively copies the iterable passed in, so future changes to it won't alter the behavior of this
     * predicate. If {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     */
    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) {
        return new AndPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if each of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is
     * found. It defensively copies the array passed in, so future changes to it won't alter the behavior of this
     * predicate. If {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     */
    public static <T> Predicate<T> and(Predicate<? super T>... components) {
        return new AndPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if both of its components evaluate to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is
     * found.
     */
    public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return new AndPredicate<T>(Predicates.<T>asList(checkNotNull(first), checkNotNull(second)));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if any one of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found.
     * It defensively copies the iterable passed in, so future changes to it won't alter the behavior of this predicate.
     * If {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     */
    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) {
        return new OrPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if any one of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found.
     * It defensively copies the array passed in, so future changes to it won't alter the behavior of this predicate. If
     * {@code components} is empty, the returned predicate will always evaluate to {@code true}.
     */
    public static <T> Predicate<T> or(Predicate<? super T>... components) {
        return new OrPredicate<T>(defensiveCopy(components));
    }

    /**
     * Returns a predicate that evaluates to {@code true} if either of its components evaluates to {@code true}. The
     * components are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found.
     */
    public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second) {
        return new OrPredicate<T>(Predicates.<T>asList(first, second));
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
            return "Not(" + predicate.toString() + ")";
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
            for (int i = 0; i < components.size(); i++) {
                if (!components.get(i).apply(t, game)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "And(" + commaJoin(components) + ")";
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
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i).apply(t, game)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Or(" + commaJoin(components) + ")";
        }
        private static final long serialVersionUID = 0;
    }

    @SuppressWarnings("unchecked")
    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) {
        return Arrays.<Predicate<? super T>>asList(first, second);
    }

    private static <T> List<T> defensiveCopy(T... array) {
        return defensiveCopy(Arrays.asList(array));
    }

    static <T> List<T> defensiveCopy(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<T>();
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
        for (int i = 0; i < components.size(); i++) {
            sb.append(components.get(i).toString());
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
