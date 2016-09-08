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
package mage.abilities.effects.common;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.MeldCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class MeldEffect extends OneShotEffect {

    private final String meldWithName;
    private final MeldCard meldCard;

    public MeldEffect(String meldWithName, MeldCard meldCard) {
        super(Outcome.Benefit);
        this.meldWithName = meldWithName;
        this.meldCard = meldCard;
    }

    public MeldEffect(final MeldEffect effect) {
        super(effect);
        this.meldWithName = effect.meldWithName;
        this.meldCard = effect.meldCard;
    }

    @Override
    public MeldEffect copy() {
        return new MeldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Find the two permanents to meld.
            UUID sourceId = source.getSourceId();
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature named " + meldWithName);
            filter.add(new NamePredicate(meldWithName));
            TargetPermanent target = new TargetControlledCreaturePermanent(filter);
            Set<UUID> meldWithList = target.possibleTargets(sourceId, source.getControllerId(), game);
            if (meldWithList.isEmpty()) {
                return false; // possible permanent has left the battlefield meanwhile
            }
            UUID meldWithId;
            if (meldWithList.size() == 1) {
                meldWithId = meldWithList.iterator().next();
            } else {
                controller.choose(Outcome.BoostCreature, target, sourceId, game);
                meldWithId = target.getFirstTarget();
            }
            // Exile the two permanents to meld.
            Permanent sourcePermanent = game.getPermanent(sourceId);
            Permanent meldWithPermanent = game.getPermanent(meldWithId);
            if (sourcePermanent != null && meldWithPermanent != null) {
                sourcePermanent.moveToExile(null, "", sourceId, game);
                meldWithPermanent.moveToExile(null, "", sourceId, game);
                // Create the meld card and move it to the battlefield.
                Card sourceCard = game.getExile().getCard(sourceId, game);
                Card meldWithCard = game.getExile().getCard(meldWithId, game);
                if (!sourceCard.isCopy() && !meldWithCard.isCopy()) {
                    meldCard.setOwnerId(controller.getId());
                    meldCard.setTopHalfCard(meldWithCard, game);
                    meldCard.setBottomHalfCard(sourceCard, game);
                    meldCard.setMelded(true);
                    game.addMeldCard(meldCard.getId(), meldCard);
                    game.getState().addCard(meldCard);
                    meldCard.setZone(Zone.EXILED, game);
                    meldCard.moveToZone(Zone.BATTLEFIELD, sourceId, game, false);
                }
                return true;
            }
        }
        return false;
    }
}
