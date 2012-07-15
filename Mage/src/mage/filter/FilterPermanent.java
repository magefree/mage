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
import mage.Constants.TargetController;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterPermanent extends FilterObject<Permanent> {
    protected List<ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>>> extraPredicates = new ArrayList<ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>>>();
    protected List<UUID> controllerId = new ArrayList<UUID>();
    protected boolean notController;
    protected TargetController controller = TargetController.ANY;
    protected TargetController owner = TargetController.ANY;

    public FilterPermanent() {
        super("permanent");
    }

    public FilterPermanent(final FilterPermanent filter) {
        super(filter);
        this.controllerId  = new ArrayList<UUID>(filter.controllerId);
        this.notController = filter.notController;
        this.controller = filter.controller;
        this.owner = filter.owner;
        this.extraPredicates = new ArrayList<ObjectSourcePlayerPredicate<ObjectSourcePlayer<Permanent>>>(extraPredicates);
    }

    public FilterPermanent(String name) {
        super(name);
    }

    @Override
    public boolean match(Permanent permanent, Game game) {
        if (!super.match(permanent, game))
            return notFilter;

        if (controllerId.size() > 0 && controllerId.contains(permanent.getControllerId()) == notController)
            return notFilter;

        return !notFilter;
    }

    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (!this.match(permanent, game))
            return notFilter;

        if (controller != TargetController.ANY && playerId != null) {
            switch(controller) {
                case YOU:
                    if (!permanent.getControllerId().equals(playerId))
                        return notFilter;
                    break;
                case OPPONENT:
                    if (!game.getOpponents(playerId).contains(permanent.getControllerId()))
                        return notFilter;
                    break;
                case NOT_YOU:
                    if (permanent.getControllerId().equals(playerId))
                        return notFilter;
                    break;
            }
        }

        if (owner != TargetController.ANY && playerId != null) {
            switch(owner) {
                case YOU:
                    if (!permanent.getOwnerId().equals(playerId))
                        return notFilter;
                    break;
                case OPPONENT:
                    if (!game.getOpponents(playerId).contains(permanent.getOwnerId()))
                        return notFilter;
                    break;
                case NOT_YOU:
                    if (permanent.getOwnerId().equals(playerId))
                        return notFilter;
                    break;
            }
        }

        return Predicates.and(extraPredicates).apply(new ObjectSourcePlayer(permanent, sourceId, playerId), game);
    }

    public void add(ObjectSourcePlayerPredicate predicate) {
        extraPredicates.add(predicate);
    }

    public List<UUID> getControllerId() {
        return controllerId;
    }

    public void setNotController(boolean notController) {
        this.notController = notController;
    }

    public void setTargetController(TargetController controller) {
        this.controller = controller;
    }

    public void setTargetOwner(TargetController owner) {
        this.owner = owner;
    }

    public boolean matchController(UUID testControllerId) {
        if (controllerId.size() > 0 && controllerId.contains(testControllerId) == notController)
            return false;
        return true;
    }

    @Override
    public FilterPermanent copy() {
        return new FilterPermanent(this);
    }

}
