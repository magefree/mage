/*
 *
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 *
 */
package mage.target.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetImpl;

/**
 *
 * @author emerald000
 */
public class TargetPermanentOrSuspendedCard extends TargetImpl {
    
    protected final FilterPermanentOrSuspendedCard filter;
    
    public TargetPermanentOrSuspendedCard() {
        this(new FilterPermanentOrSuspendedCard(), false);
    }
    
    public TargetPermanentOrSuspendedCard(FilterPermanentOrSuspendedCard filter, boolean notTarget) {
        super(notTarget);
        this.filter = filter;
        this.zone = Zone.ALL;
        this.targetName = filter.getMessage();
        this.minNumberOfTargets = 1;
        this.maxNumberOfTargets = 1;
    }
    
    public TargetPermanentOrSuspendedCard(final TargetPermanentOrSuspendedCard target) {
        super(target);
        this.filter = target.filter.copy();
    }
    
    @Override
    public Filter<MageObject> getFilter() {
        return this.filter;
    }
    
    @Override
    public TargetPermanentOrSuspendedCard copy() {
        return new TargetPermanentOrSuspendedCard(this);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        MageObject sourceObject = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(sourceObject, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                return true;
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, sourceId, sourceControllerId, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> possibleTargets = new HashSet<>(20);
        MageObject sourceObject = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter.getPermanentFilter(), sourceControllerId, game)) {
            if (permanent.canBeTargetedBy(sourceObject, sourceControllerId, game) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        for (Card card : game.getExile().getAllCards(game)) {
            if (filter.match(card, sourceId, sourceControllerId, game)) {
                possibleTargets.add(card.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canTarget(UUID id, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Card card = game.getExile().getCard(id, game);
        return card != null && filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (source != null) {
                MageObject targetSource = game.getObject(source.getSourceId());
                return permanent.canBeTargetedBy(targetSource, source.getControllerId(), game)
                        && filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
            } else {
                return filter.match(permanent, game);
            }
        }
        Card card = game.getExile().getCard(id, game);
        return card != null && filter.match(card, game);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return this.canTarget(id, source, game);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Game game) {
        return this.canChoose(null, sourceControllerId, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
        return this.possibleTargets(null, sourceControllerId, game);
    }

    @Override
    public String getTargetedName(Game game) {
        StringBuilder sb = new StringBuilder("");
        for (UUID targetId : this.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sb.append(permanent.getLogName()).append(' ');
            } else {
                Card card = game.getExile().getCard(targetId, game);
                if (card != null) {
                    sb.append(card.getLogName()).append(' ');
                }
            }
        }
        return sb.toString();
    }
}
