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
package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author noxx
 */
public class DiscardCardYouChooseTargetEffect extends OneShotEffect<DiscardCardYouChooseTargetEffect> {

    private FilterCard filter;
    private TargetController targetController;

    public DiscardCardYouChooseTargetEffect() {
        this(new FilterCard("a card"));
    }

    public DiscardCardYouChooseTargetEffect(TargetController targetController) {
        this(new FilterCard("a card"), targetController);
    }

    public DiscardCardYouChooseTargetEffect(FilterCard filter) {
        this(filter, TargetController.OPPONENT);
    }

    public DiscardCardYouChooseTargetEffect(FilterCard filter, TargetController targetController) {
        super(Outcome.Discard);
        this.targetController = targetController;
        this.filter = filter;
        staticText = this.setText();
    }

    public DiscardCardYouChooseTargetEffect(final DiscardCardYouChooseTargetEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetController = effect.targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player != null && you != null) {
            player.revealCards(sourceCard != null ? sourceCard.getName() :"Discard", player.getHand(), game);
            if (player.getHand().count(filter, source.getSourceId(), source.getControllerId(), game) > 0) {
                TargetCard target = new TargetCard(Zone.PICK, filter);
                                target.setRequired(true);
                if (you.choose(Outcome.Benefit, player.getHand(), target, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        return player.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DiscardCardYouChooseTargetEffect copy() {
        return new DiscardCardYouChooseTargetEffect(this);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Target ");
        switch(targetController) {
            case OPPONENT:
                sb.append("Opponent");
                break;
            case ANY:
                sb.append("Player");
                break;
            default:
                throw new UnsupportedOperationException("target controller not supported");
        }
        sb.append(" reveals his or her hand. You choose ")
                .append(filter.getMessage()).append(" from it. That player discards that card").toString();
        return sb.toString();
    }
}
