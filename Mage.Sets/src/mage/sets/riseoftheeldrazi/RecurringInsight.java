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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public class RecurringInsight extends CardImpl<RecurringInsight> {

    public RecurringInsight(UUID ownerId) {
        super(ownerId, 82, "Recurring Insight", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");
        this.expansionSetCode = "ROE";

        this.color.setBlue(true);
        
        // Rebound
        this.addAbility(new ReboundAbility());

        // Draw cards equal to the number of cards in target opponent's hand.
        this.getSpellAbility().addEffect(new RecurringInsightEffect());    
    }

    public RecurringInsight(final RecurringInsight card) {
        super(card);
    }

    @Override
    public RecurringInsight copy() {
        return new RecurringInsight(this);
    }
}

class RecurringInsightEffect extends OneShotEffect<RecurringInsightEffect> {

    public RecurringInsightEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "Draw cards equal to the number of cards in target opponent's hand";
    }

    public RecurringInsightEffect(final RecurringInsightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetOpponent target = new TargetOpponent();
        Player you = game.getPlayer(source.getControllerId());
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            you.chooseTarget(Constants.Outcome.DrawCard, target, source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                you.drawCards(opponent.getHand().size(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public RecurringInsightEffect copy() {
        return new RecurringInsightEffect(this);
    }

}
