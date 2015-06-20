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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RavagingBlaze extends CardImpl {

    public RavagingBlaze(UUID ownerId) {
        super(ownerId, 159, "Ravaging Blaze", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");
        this.expansionSetCode = "ORI";

        // Ravaging Blaze deals X damage to target creature. 
        // <i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, Ravaging Blaze also deals X damage to that creature's controller.
        this.getSpellAbility().addEffect(new RavagingBlazeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public RavagingBlaze(final RavagingBlaze card) {
        super(card);
    }

    @Override
    public RavagingBlaze copy() {
        return new RavagingBlaze(this);
    }
}

class RavagingBlazeEffect extends OneShotEffect {

    public RavagingBlazeEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target creature.<br>"
                + "<i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, {this} also deals X damage to that creature's controller.";
    }

    public RavagingBlazeEffect(final RavagingBlazeEffect effect) {
        super(effect);
    }

    @Override
    public RavagingBlazeEffect copy() {
        return new RavagingBlazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            int xValue = source.getManaCostsToPay().getX();
            if (xValue > 0) {
                permanent.damage(xValue, source.getSourceId(), game, false, true);
                if (SpellMasteryCondition.getInstance().apply(game, source)) {
                    Player targetController = game.getPlayer(permanent.getControllerId());
                    if (targetController != null) {
                        targetController.damage(xValue, source.getSourceId(), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

}
