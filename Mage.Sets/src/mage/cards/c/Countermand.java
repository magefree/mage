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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class Countermand extends CardImpl {

    public Countermand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Counter target spell. Its controller puts the top four cards of his or her library into his or her graveyard.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL));
        this.getSpellAbility().addEffect(new CountermandEffect());
    }

    public Countermand(final Countermand card) {
        super(card);
    }

    @Override
    public Countermand copy() {
        return new Countermand(this);
    }
}
class CountermandEffect extends OneShotEffect {

    public CountermandEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell. Its controller puts the top four cards of his or her library into his or her graveyard";
    }

    public CountermandEffect(final CountermandEffect effect) {
        super(effect);
    }

    @Override
    public CountermandEffect copy() {
        return new CountermandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean countered = false;
        StackObject stackObject = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
            countered = true;
        }
        if (stackObject != null) {
            Player controller = game.getPlayer(stackObject.getControllerId());
            if (controller != null) {
                controller.moveCards(controller.getLibrary().getTopCards(game, 4), Zone.GRAVEYARD, source, game);
            }
        }
        return countered;
    }
}
