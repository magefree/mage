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

package mage.target;

import java.util.*;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetAmount<T extends TargetAmount<T>> extends TargetImpl<T> {

	int amount;
	int remainingAmount;

	public TargetAmount(int amount) {
		this.amount = amount;
		this.remainingAmount = amount;
		this.required = true;
	}

	public TargetAmount(final TargetAmount target) {
		super(target);
		this.amount = target.amount;
		this.remainingAmount = target.remainingAmount;
	}

	public int getAmountRemaining() {
		return remainingAmount;
	}

	@Override
	public boolean isChosen() {
		return doneChosing();
	}

	@Override
	public boolean doneChosing() {
		return remainingAmount == 0;
	}

	@Override
	public void clearChosen() {
		super.clearChosen();
		remainingAmount = amount;
	}

	@Override
	public void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent) {
		if (amount <= remainingAmount) {
			super.addTarget(id, amount, source, game, skipEvent);
			remainingAmount -= amount;
		}
	}

	@Override
	public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
		Player player = game.getPlayer(playerId);
		chosen = remainingAmount == 0;
		while (remainingAmount > 0) {
			if (!player.chooseTargetAmount(outcome, this, source, game)) {
				return chosen;
			}
			chosen = remainingAmount == 0;
		}
		return chosen = true;
	}
    
    @Override
    public List<T> getTargetOptions(Ability source, Game game) {
        List<T> options = new ArrayList<T>();
        Set<UUID> possibleTargets = possibleTargets(source.getSourceId(), source.getControllerId(), game);
        
        addTargets(this, possibleTargets, options, source, game);
        
        return options;
    }

    protected void addTargets(TargetAmount<T> target, Set<UUID> targets, List<T> options, Ability source, Game game) {
        for (UUID targetId: targets) {
            for (int n = 1; n <= target.remainingAmount; n++) {
                T t = target.copy();
                t.addTarget(targetId, n, source, game, true);
                if (t.remainingAmount > 0) {
                    if (targets.size() > 1) {
                        Set<UUID> newTargets = new HashSet<UUID>();
                        for (UUID newTarget: targets) {
                            if (!newTarget.equals(targetId))
                                newTargets.add(newTarget);
                        }
                        addTargets(t, newTargets, options, source, game);
                    }
                }
                else {
                    options.add(t);
                }
            }
        }
    }
}
