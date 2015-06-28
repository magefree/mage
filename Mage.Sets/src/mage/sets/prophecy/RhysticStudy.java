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
package mage.sets.prophecy;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class RhysticStudy extends CardImpl {

    public RhysticStudy(UUID ownerId) {
        super(ownerId, 45, "Rhystic Study", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "PCY";

        // Whenever an opponent casts a spell, you may draw a card unless that player pays {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new RhysticStudyDrawEffect(), new FilterSpell(), false, SetTargetPointer.PLAYER));
    }

    public RhysticStudy(final RhysticStudy card) {
        super(card);
    }

    @Override
    public RhysticStudy copy() {
        return new RhysticStudy(this);
    }
}

class RhysticStudyDrawEffect extends OneShotEffect {

    public RhysticStudyDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may draw a card unless that player pays {1}";
    }
    
    public RhysticStudyDrawEffect(final RhysticStudyDrawEffect effect) {
        super(effect);
    }

    @Override
    public RhysticStudyDrawEffect copy() {
        return new RhysticStudyDrawEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            Cost cost = new GenericManaCost(1);
            String message = "Would you like to pay {1} to prevent the opponent to draw a card?";
            if (!(opponent.chooseUse(Outcome.Benefit, message, source, game) && cost.pay(source, game, source.getSourceId(), opponent.getId(), false))) {
                if(controller.chooseUse(Outcome.DrawCard, "Draw a card (" + sourceObject.getLogName() +")", source, game)) {
                    controller.drawCards(1, game);
                }
            }
            return true;
        }
        return false;
    }
    
}
