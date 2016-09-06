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
package mage.sets.futuresight;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Derpthemeus
 */
public class MinionsMurmurs extends CardImpl {

    public MinionsMurmurs(UUID ownerId) {
        super(ownerId, 71, "Minions' Murmurs", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "FUT";

        // You draw X cards and you lose X life, where X is the number of creatures you control.
        this.getSpellAbility().addEffect(new MinionsMurmursEffect());
    }

    public MinionsMurmurs(final MinionsMurmurs card) {
        super(card);
    }

    @Override
    public MinionsMurmurs copy() {
        return new MinionsMurmurs(this);
    }

    class MinionsMurmursEffect extends OneShotEffect {

        public MinionsMurmursEffect() {
            super(Outcome.DrawCard);
            this.staticText = "You draw X cards and you lose X life, where X is the number of creatures you control";
        }

        public MinionsMurmursEffect(final MinionsMurmursEffect effect) {
            super(effect);
        }

        @Override
        public MinionsMurmursEffect copy() {
            return new MinionsMurmursEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int creaturesControlled = game.getBattlefield().countAll(new FilterCreaturePermanent(), controller.getId(), game);
                controller.drawCards(creaturesControlled, game);
                controller.loseLife(creaturesControlled, game);
                return true;
            }
            return false;
        }
    }
}
