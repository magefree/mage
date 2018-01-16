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

import java.util.ArrayList;
import java.util.List;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 * @param <E>
 */
public abstract class FilterImpl<E> implements Filter<E> {

    protected List<Predicate<Object>> predicates = new ArrayList<>();
    protected String message;
    protected boolean lockedFilter = false; // Helps to prevent to "accidently" modify the StaticFilters objects

    @Override
    public abstract FilterImpl<E> copy();

    public FilterImpl(String name) {
        this.message = name;
    }

    public FilterImpl(final FilterImpl<E> filter) {
        this.message = filter.message;
        this.predicates = new ArrayList<>(filter.predicates);
        this.lockedFilter = false;// After copying a filter it's allowed to modify
    }

    @Override
    public boolean match(E e, Game game) {
        if (checkObjectClass(e)) {
            return Predicates.and(predicates).apply(e, game);
        }
        return false;
    }

    @Override
    public final Filter add(Predicate predicate) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        predicates.add(predicate);
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        if (isLockedFilter()) {
            throw new UnsupportedOperationException("You may not modify a locked filter");
        }
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public boolean isLockedFilter() {
        return lockedFilter;
    }

    public void setLockedFilter(boolean lockedFilter) {
        this.lockedFilter = lockedFilter;
    }

}
