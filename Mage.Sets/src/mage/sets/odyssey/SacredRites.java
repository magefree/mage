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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author cbt33
 */
public class SacredRites extends CardImpl<SacredRites> {

    public SacredRites(UUID ownerId) {
        super(ownerId, 44, "Sacred Rites", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);

        // Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.
        this.getSpellAbility().addTarget(new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard()));
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

class SacredRitesEffect extends OneShotEffect<SacredRitesEffect> {
    
    public SacredRitesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.";
    }
    
    public SacredRitesEffect(final SacredRitesEffect effect) {
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
            Targets targets = source.getTargets();
            int i = 0;
                for (Target target: targets) {
                    Card card = game.getCard(target.getFirstTarget());
                    player.discard(card, source, game);
                    i++;
                }
            game.addEffect(new BoostControlledEffect(0, i, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
