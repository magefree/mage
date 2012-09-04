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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public class Subversion extends CardImpl<Subversion> {

    public Subversion(UUID ownerId) {
        super(ownerId, 68, "Subversion", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "ULG";

        this.color.setBlack(true);

        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SubversionEffect(), Constants.TargetController.YOU, false));
    }

    public Subversion(final Subversion card) {
        super(card);
    }

    @Override
    public Subversion copy() {
        return new Subversion(this);
    }
    
    
    class SubversionEffect extends OneShotEffect<SubversionEffect> {

        public SubversionEffect() {
            super(Outcome.Damage);
            staticText = "Each opponent loses 1 life. You gain life equal to the life lost this way";
        }

        public SubversionEffect(final SubversionEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int damage = 0;
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                damage += game.getPlayer(opponentId).damage(1, source.getSourceId(), game, false, true);
            }
            game.getPlayer(source.getControllerId()).gainLife(damage, game);
            return true;
        }

        @Override
        public SubversionEffect copy() {
            return new SubversionEffect(this);
        }

    }
}
