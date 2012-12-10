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
package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;


/**
 *
 * @author Plopman
 */
public class RetraceAbility extends ActivatedAbilityImpl<RetraceAbility> {

    public RetraceAbility(Cost cost, Constants.TimingRule timingRule) {
        super(Constants.Zone.GRAVEYARD, new RetraceEffect(), cost);
        super.addCost(new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        this.timing = timingRule;
        this.usesStack = false;
    }

    public RetraceAbility(final RetraceAbility ability) {
        super(ability);
    }

    @Override
    public RetraceAbility copy() {
        return new RetraceAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder("Retrace");
        
        return sbRule.toString();
    }
}
class RetraceEffect extends OneShotEffect<RetraceEffect> {

    public RetraceEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "";
    }

    public RetraceEffect(final RetraceEffect effect) {
        super(effect);
    }

    @Override
    public RetraceEffect copy() {
        return new RetraceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) game.getObject(source.getSourceId());
        if (card != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                card.getSpellAbility().clear();
                int amount = source.getManaCostsToPay().getX();
                card.getSpellAbility().getManaCostsToPay().setX(amount);
                for (Target target : card.getSpellAbility().getTargets()) {
                    target.setRequired(true);
                }
                return controller.cast(card.getSpellAbility(), game, true);
            }
        }
        return false;
    }
}

