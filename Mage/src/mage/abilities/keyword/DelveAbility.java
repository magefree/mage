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
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;


/**
 * 702.64. Delve
 *
 * 702.64a Delve is a static ability that functions while the spell that has delve is on
 * the stack. "Delve" means "As an additional cost to cast this spell, you may exile any
 * number of cards from your graveyard. Each card exiled this way reduces the cost to cast
 * this spell by {1}." Using the delve ability follows the rules for paying additional
 * costs in rules 601.2b and 601.2e-g. #
 *
 * 702.64b Multiple instances of delve on the same spell are redundant.
 *
 * @author LevelX2
 *
 */

 public class DelveAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public DelveAbility() {
        super(Zone.STACK, null);
        this.setRuleAtTheTop(true);
    }

    public DelveAbility(final DelveAbility ability) {
      super(ability);
    }

    @Override
    public DelveAbility copy() {
      return new DelveAbility(this);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player == null || !(ability instanceof SpellAbility)) {
            return;
        }
        Target target = new TargetCardInYourGraveyard(1, Integer.MAX_VALUE, new FilterCard());
        target.setTargetName("cards to delve from your graveyard");
        if (!target.canChoose(sourceId, controllerId, game)) {
            return;
        }
        if (player.chooseUse(Outcome.Detriment, "Delve cards from your graveyard?", game)) {
            player.chooseTarget(Outcome.Detriment, target, ability, game);
            if (target.getTargets().size() > 0) {
                int adjCost = 0;
                for (UUID cardId: target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card == null) {
                        continue;
                    }
                    card.moveToExile(null, null, this.getSourceId(), game);
                    ++adjCost;
                }
                game.informPlayers(new StringBuilder(player.getName()).append(" delved ")
                        .append(adjCost).append(" creature").append(adjCost != 1?"s":"").append(" from his or her graveyard").toString());
                CardUtil.adjustCost((SpellAbility)ability, adjCost);
            }
        }
    }

    @Override
    public String getRule() {
      return "Delve <i>(You may exile any number of cards from your graveyard as you cast this spell. It costs {1} less to cast for each card exiled this way.)</i>";
    }
}
