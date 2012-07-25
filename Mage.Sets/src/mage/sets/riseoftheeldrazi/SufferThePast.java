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
package mage.sets.riseoftheeldrazi;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class SufferThePast extends CardImpl<SufferThePast> {

    public SufferThePast(UUID ownerId) {
        super(ownerId, 128, "Suffer the Past", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{B}");
        this.expansionSetCode = "ROE";

        this.color.setBlack(true);

        // Exile X target cards from target player's graveyard. For each card exiled this way, that player loses 1 life and you gain 1 life.
        this.getSpellAbility().addEffect(new SufferThePastEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public SufferThePast(final SufferThePast card) {
        super(card);
    }

    @Override
    public SufferThePast copy() {
        return new SufferThePast(this);
    }
}

class SufferThePastEffect extends OneShotEffect<SufferThePastEffect> {
    
    public SufferThePastEffect() {
        super(Constants.Outcome.Neutral);
        this.staticText = "Exile X target cards from target player's graveyard. For each card exiled this way, that player loses 1 life and you gain 1 life";
    }

    public SufferThePastEffect(final SufferThePastEffect effect) {
        super(effect);
    }

    @Override
    public SufferThePastEffect copy() {
        return new SufferThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("card in target player's graveyard");
        int numberExiled = 0;
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            int numberToTarget = Math.min(targetPlayer.getGraveyard().size(), source.getManaCostsToPay().getX());
            TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(numberToTarget, numberToTarget, filter);
            if (you != null) {
                if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Neutral, source.getControllerId(), source.getId(), game)) {
                    if (!target.getTargets().isEmpty()) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                card.moveToExile(id, "Suffer the Past", source.getId(), game);
                                numberExiled ++;
                            }
                        }
                        you.gainLife(numberExiled, game);
                        targetPlayer.loseLife(numberExiled, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}


