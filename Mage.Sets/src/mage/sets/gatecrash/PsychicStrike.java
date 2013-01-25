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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class PsychicStrike extends CardImpl<PsychicStrike> {

    public PsychicStrike(UUID ownerId) {
        super(ownerId, 189, "Psychic Strike", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Counter target spell. Its controller puts the top two cards of his or her library into his or her graveyard.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterSpell()));
        this.getSpellAbility().addEffect(new PsychicStrikeEffect());
    }

    public PsychicStrike(final PsychicStrike card) {
        super(card);
    }

    @Override
    public PsychicStrike copy() {
        return new PsychicStrike(this);
    }
}

class PsychicStrikeEffect extends OneShotEffect<PsychicStrikeEffect> {

    public PsychicStrikeEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Its controller puts the top two cards of his or her library into his or her graveyard";
    }

    public PsychicStrikeEffect(final PsychicStrikeEffect effect) {
        super(effect);
    }

    @Override
    public PsychicStrikeEffect copy() {
        return new PsychicStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackObject != null && game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
            Player controller = game.getPlayer(stackObject.getControllerId());
            if (controller == null) {
                return false;
            }
            int cardsCount = Math.min(2, controller.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = controller.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                }
            }
            return true;            
        }
        return false;
    }
}
