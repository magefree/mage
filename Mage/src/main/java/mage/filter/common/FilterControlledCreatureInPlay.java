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
package mage.filter.common;

import java.util.UUID;
import mage.constants.TargetController;
import mage.filter.FilterImpl;
import mage.filter.FilterInPlay;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class FilterControlledCreatureInPlay extends FilterImpl<Object> implements FilterInPlay<Object> {

    protected final FilterCreaturePermanent creatureFilter;

    public FilterControlledCreatureInPlay() {
        this("creature");
    }

    public FilterControlledCreatureInPlay(String name) {
        super(name);
        creatureFilter = new FilterCreaturePermanent();
        creatureFilter.add(new ControllerPredicate(TargetController.YOU));
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Permanent;
    }

    public FilterControlledCreatureInPlay(final FilterControlledCreatureInPlay filter) {
        super(filter);
        this.creatureFilter = filter.creatureFilter.copy();
    }

    @Override
    public boolean match(Object o, Game game) {
        if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, game);
        }
        return false;
    }

    @Override
    public boolean match(Object o, UUID sourceId, UUID playerId, Game game) {
        if (o instanceof Permanent) {
            return creatureFilter.match((Permanent) o, sourceId, playerId, game);
        }
        return false;
    }

    public FilterCreaturePermanent getCreatureFilter() {
        return this.creatureFilter;
    }

    @Override
    public FilterControlledCreatureInPlay copy() {
        return new FilterControlledCreatureInPlay(this);
    }

}
