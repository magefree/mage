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

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author Plopmans
 */
public class ReturnToHandChosenPermanentEffect extends OneShotEffect {

    protected final FilterControlledPermanent filter;
    protected int number;

    public ReturnToHandChosenPermanentEffect(FilterControlledPermanent filter) {
        this(filter, 1);
    }

    public ReturnToHandChosenPermanentEffect(FilterControlledPermanent filter, int number) {
        super(Outcome.ReturnToHand);
        this.filter = filter;
        this.number = number;
        this.staticText = getText();
    }

    public ReturnToHandChosenPermanentEffect(final ReturnToHandChosenPermanentEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.number = effect.number;
    }

    @Override
    public ReturnToHandChosenPermanentEffect copy() {
        return new ReturnToHandChosenPermanentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            int available = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
            if (available > 0) {
                TargetControlledPermanent target = new TargetControlledPermanent(Math.min(number, available), number, filter, true);
                if (player.chooseTarget(this.outcome, target, source, game)) {
                    player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }

    protected String getText() {
        StringBuilder sb = new StringBuilder("that player returns ");
        if (!filter.getMessage().startsWith("another")) {
            sb.append(CardUtil.numberToText(number, "a"));
        }
        sb.append(" ").append(filter.getMessage());
        sb.append(" he or she controls");
        if (number > 1) {
            sb.append(" to their owner's hand");
        } else {
            sb.append(" to its owner's hand");
        }
        return sb.toString();
    }
}
