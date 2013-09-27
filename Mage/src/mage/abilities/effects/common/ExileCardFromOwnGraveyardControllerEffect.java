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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ExileCardFromOwnGraveyardControllerEffect extends OneShotEffect<ExileCardFromOwnGraveyardControllerEffect> {
    private int amount;

    public ExileCardFromOwnGraveyardControllerEffect(int amount) {
        super(Outcome.Exile);
        this.amount = amount;
        this.staticText = setText();
    }

    public ExileCardFromOwnGraveyardControllerEffect(final ExileCardFromOwnGraveyardControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public ExileCardFromOwnGraveyardControllerEffect copy() {
        return new ExileCardFromOwnGraveyardControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null){
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(Math.min(amount, player.getGraveyard().size()), new FilterCard());
            target.setRequired(true);
            if (player.chooseTarget(outcome, target, source, game)) {
                for (UUID targetId: target.getTargets()) {
                    Card card = player.getGraveyard().get(targetId, game);
                    if (card != null) {
                        card.moveToZone(Zone.EXILED, source.getSourceId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Exile ");
        if (amount == 1) {
            sb.append(" a card ");
        } else {
            sb.append(CardUtil.numberToText(amount)). append(" cards ");
        }
        sb.append("from your graveyard");
        return sb.toString();
    }

}
