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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author cbt33
 */
public class SacredRites extends CardImpl {

    public SacredRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.
        this.getSpellAbility().addEffect(new SacredRitesEffect());
    }

    public SacredRites(final SacredRites card) {
        super(card);
    }

    @Override
    public SacredRites copy() {
        return new SacredRites(this);
    }
}

class SacredRitesEffect extends OneShotEffect {
    
    SacredRitesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.";
    }
    
    SacredRitesEffect(final SacredRitesEffect effect) {
        super(effect);
    }
    
    @Override
    public SacredRitesEffect copy() {
        return new SacredRitesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard("cards to discard"));
            while (player.canRespond() && !target.isChosen()) {
                target.choose(Outcome.BoostCreature, player.getId(), source.getSourceId(), game);
            }
            int numDiscarded = 0;
                for (UUID targetId : target.getTargets()) {
                    Card card = player.getHand().get(targetId, game);
                    if (player.discard(card, source, game)) {
                        numDiscarded++;
                    }
                }
            game.addEffect(new BoostControlledEffect(0, numDiscarded, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
