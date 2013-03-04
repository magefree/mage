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
package mage.abilities.effects.common;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FightTargetsEffect extends OneShotEffect<FightTargetsEffect> {

    public FightTargetsEffect() {
        super(Outcome.Damage);
    }

    public FightTargetsEffect(final FightTargetsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            // only if both targets are legal the effect will be applied
            if (source.getTargets().get(0).isLegal(source, game) && source.getTargets().get(1).isLegal(source, game)) {
                Permanent creature1 = game.getPermanent(source.getTargets().get(0).getFirstTarget());
                Permanent creature2 = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                // 20110930 - 701.10
                if (creature1 != null && creature2 != null) {
                    if (creature1.getCardType().contains(CardType.CREATURE) && creature2.getCardType().contains(CardType.CREATURE)) {
                        creature1.damage(creature2.getPower().getValue(), creature2.getId(), game, true, false);
                        creature2.damage(creature1.getPower().getValue(), creature1.getId(), game, true, false);
                        return true;
                    }
                }
            }
            game.informPlayers(card.getName() + " has been fizzled.");
        }
        return false;
    }

    @Override
    public FightTargetsEffect copy() {
        return new FightTargetsEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Target " + mode.getTargets().get(0).getTargetName() + " fights another target " + mode.getTargets().get(1).getTargetName();
    }

}

