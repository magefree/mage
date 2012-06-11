/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DiscardTargetEffect extends OneShotEffect<DiscardTargetEffect> {

    protected DynamicValue amount;
	protected boolean randomDiscard;

    public DiscardTargetEffect(DynamicValue amount) {
        this(amount, false);
    }

	public DiscardTargetEffect(DynamicValue amount, boolean randomDiscard) {
        super(Outcome.Discard);
		this.randomDiscard = randomDiscard;
        this.amount = amount;
    }

    public DiscardTargetEffect(int amount) {
        this(new StaticValue(amount));
    }

    public DiscardTargetEffect(int amount, boolean randomDiscard) {
        this(new StaticValue(amount), randomDiscard);
    }

    public DiscardTargetEffect(final DiscardTargetEffect effect) {
        super(effect);
        this.amount = effect.amount.clone();
		this.randomDiscard = effect.randomDiscard;
    }

    @Override
    public DiscardTargetEffect copy() {
        return new DiscardTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
			if (randomDiscard) {
				int maxAmount = Math.min(amount.calculate(game, source), player.getHand().size());
				for (int i = 0; i < maxAmount; i++) {
					Card card = player.getHand().getRandom(game);
					if (card != null) {
						player.discard(card, source, game);
					}
				}
			} else {
				player.discard(amount.calculate(game, source), source, game);
			}
            
            return true;
        }
        return false;
    }

	@Override
	public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
		if(mode.getTargets().isEmpty()){
			sb.append("that player");
		} else {
			sb.append("Target ").append(mode.getTargets().get(0).getTargetName());
		}
		sb.append(" discards ");
        sb.append(amount).append(" card");
        try {
            if (Integer.parseInt(amount.toString()) > 1) {
                sb.append("s");
            }
        } catch (Exception e) {
            sb.append("s");
        }
		if (randomDiscard) {
			sb.append(" at random");
		}
        String message = amount.getMessage();
        if (message.length() > 0) {
            sb.append(" for each ");
        }
        sb.append(message);
        return sb.toString();
	}
}
