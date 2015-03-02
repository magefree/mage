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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public class ReadTheRunes extends CardImpl {

    public ReadTheRunes(UUID ownerId) {
        super(ownerId, 104, "Read the Runes", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{U}");
        this.expansionSetCode = "ONS";

        this.color.setBlue(true);

        // Draw X cards. For each card drawn this way, discard a card unless you sacrifice a permanent.
        this.getSpellAbility().addEffect(new ReadTheRunesEffect());
    }

    public ReadTheRunes(final ReadTheRunes card) {
        super(card);
    }

    @Override
    public ReadTheRunes copy() {
        return new ReadTheRunes(this);
    }
}

class ReadTheRunesEffect extends OneShotEffect {
    
    ReadTheRunesEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw X cards. For each card drawn this way, discard a card unless you sacrifice a permanent.";
    }
    
    ReadTheRunesEffect(final ReadTheRunesEffect effect) {
        super(effect);
    }
    
    @Override
    public ReadTheRunesEffect copy() {
        return new ReadTheRunesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int drawnCards = controller.drawCards(source.getManaCostsToPay().getX(), game);
            Target target = new TargetControlledPermanent(0, drawnCards, new FilterControlledPermanent(), true);
            controller.chooseTarget(Outcome.Sacrifice, target, source, game);
            int sacrificedPermanents = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.sacrifice(source.getSourceId(), game)) {
                        sacrificedPermanents++;
                    }
                }
            }
            controller.discard(drawnCards - sacrificedPermanents, false, source, game);
            return true;
        }
        return false;
    }
}
