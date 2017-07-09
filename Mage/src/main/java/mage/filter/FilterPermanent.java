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
import java.util.UUID;

import mage.constants.SubType;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class FilterPermanent extends FilterObject<Permanent> implements FilterInPlay<Permanent> {

    protected List<ObjectPlayerPredicate<ObjectPlayer<Permanent>>> extraPredicates = new ArrayList<>();

    public FilterPermanent() {
        super("permanent");
    }

    public FilterPermanent(final FilterPermanent filter) {
        super(filter);
        this.extraPredicates = new ArrayList<>(filter.extraPredicates);
    }

    public FilterPermanent(String name) {
        super(name);
    }

    public FilterPermanent(SubType subtype, String name) {
        super(name);
        this.add(new SubtypePredicate(subtype));
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(permanent, game)) {
            return false;
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(permanent, sourceId, playerId), game);
    }

    public void add(ObjectPlayerPredicate predicate) {
        extraPredicates.add(predicate);
    }

    @Override
    public FilterPermanent copy() {
        return new FilterPermanent(this);
    }

}
