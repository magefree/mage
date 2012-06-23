/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInExile extends TargetCard<TargetCardInExile> {

    private UUID zoneId;

    public TargetCardInExile(FilterCard filter, UUID zoneId) {
        this(1, 1, filter, zoneId);
    }

    public TargetCardInExile(int minNumTargets, int maxNumTargets, FilterCard filter, UUID zoneId) {
        super(minNumTargets, maxNumTargets, Zone.EXILED, filter);
        this.zoneId = zoneId;
        this.targetName = filter.getMessage();
    }

    public TargetCardInExile(final TargetCardInExile target) {
        super(target);
        this.zoneId = target.zoneId;
    }

       @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.EXILED) {
            ExileZone exile;
            if (zoneId != null) {
                exile = game.getExile().getExileZone(zoneId);
            } else {
                exile = game.getExile().getPermanentExile();
            }
            if (exile != null && exile.contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public TargetCardInExile copy() {
        return new TargetCardInExile(this);
    }
}
