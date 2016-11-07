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
package mage.target.common;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class TargetCardInGraveyardOrBattlefield extends TargetCard {

    public TargetCardInGraveyardOrBattlefield() {
        this(1, 1, new FilterCard("target card in a graveyard or permanent on the battlefield"));
    }

    public TargetCardInGraveyardOrBattlefield(FilterCard filter) {
        this(1, 1, filter);
    }

    public TargetCardInGraveyardOrBattlefield(int numTargets, FilterCard filter) {
        this(numTargets, numTargets, filter);
    }

    public TargetCardInGraveyardOrBattlefield(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter); // Zone for card
        this.targetName = filter.getMessage();
    }

    public TargetCardInGraveyardOrBattlefield(final TargetCardInGraveyardOrBattlefield target) {
        super(target);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        if (!super.canChoose(sourceId, sourceControllerId, game)) {
            MageObject targetSource = game.getObject(sourceId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
                if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(permanent, sourceControllerId, game)) {
                    return true;
                }
            }
            return false;

        }
        return true;
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game
    ) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return filter.match(permanent, game);
        }
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            return filter.match(card, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Game game
    ) {
        //return super.possibleTargets(sourceControllerId, game); //To change body of generated methods, choose Tools | Templates.
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, game);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
            if (filter.match(permanent, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game
    ) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterPermanent(), sourceControllerId, game)) {
            if ((notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) && filter.match(permanent, sourceId, sourceControllerId, game)) {
                possibleTargets.add(permanent.getId());
            }
        }
        return possibleTargets;

    }

    @Override
    public TargetCardInGraveyardOrBattlefield copy() {
        return new TargetCardInGraveyardOrBattlefield(this);
    }

}
