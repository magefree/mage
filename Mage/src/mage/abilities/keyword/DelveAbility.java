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
 * 702.65. Delve
 * 702.65a Delve is a static ability that functions while the spell with delve is on the stack.
 *         “Delve” means “For each generic mana in this spell’s total cost, you may exile a card 
 *         from your graveyard rather than pay that mana.” The delve ability isn’t an additional
 *         or alternative cost and applies only after the total cost of the spell with delve is
 *         determined.
 * 702.65b Multiple instances of delve on the same spell are redundant.
 * 
 * * The rules for delve have changed slightly since it was last in an expansion. Previously, delve
 *   reduced the cost to cast a spell. Under the current rules, you exile cards from your graveyard
 *   at the same time you pay the spell’s cost. Exiling a card this way is simply another way to pay
 *   that cost.
 * * Delve doesn’t change a spell’s mana cost or converted mana cost. For example, Dead Drop’s converted
 *   mana cost is 10 even if you exiled three cards to cast it.
 * * You can’t exile cards to pay for the colored mana requirements of a spell with delve.
 * * You can’t exile more cards than the generic mana requirement of a spell with delve. For example,
 *   you can’t exile more than nine cards from your graveyard to cast Dead Drop.
 * * Because delve isn’t an alternative cost, it can be used in conjunction with alternative costs.
 * 
 * @author LevelX2
 *
 *  TODO: Change card exiling to a way to pay mana costs, now it's maybe not passible to pay costs from effects that 
 * increase the mana costs.
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
        target.setNotTarget(true);
        if (!target.canChoose(sourceId, controllerId, game)) {
            return;
        }
        if (!CardUtil.isCheckPlayableMode(ability) && player.chooseUse(Outcome.Detriment, "Delve cards from your graveyard?", game)) {
            player.chooseTarget(Outcome.Detriment, target, ability, game);
            if (target.getTargets().size() > 0) {
                int adjCost = 0;
                for (UUID cardId: target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card == null) {
                        continue;
                    }
                    player.moveCardToExileWithInfo(card, null, "", getSourceId(), game, Zone.GRAVEYARD);
                    ++adjCost;
                }
                game.informPlayers(new StringBuilder("Delve: ").append(player.getName()).append(" exiled ")
                        .append(adjCost).append(" card").append(adjCost != 1?"s":"").append(" from his or her graveyard").toString());
                CardUtil.adjustCost((SpellAbility)ability, adjCost);
            }
        }
    }

    @Override
    public String getRule() {
      return "Delve <i>(You may exile any number of cards from your graveyard as you cast this spell. It costs {1} less to cast for each card exiled this way.)</i>";
    }
}
