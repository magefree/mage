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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public class FoldIntoAether extends CardImpl {

    public FoldIntoAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Counter target spell. If that spell is countered this way, its controller may put a creature card from his or her hand onto the battlefield.
        this.getSpellAbility().addEffect(new FoldIntoAetherEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public FoldIntoAether(final FoldIntoAether card) {
        super(card);
    }

    @Override
    public FoldIntoAether copy() {
        return new FoldIntoAether(this);
    }
}

class FoldIntoAetherEffect extends OneShotEffect {

    public FoldIntoAetherEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If that spell is countered this way, its controller may put a creature card from his or her hand onto the battlefield";
    }

    public FoldIntoAetherEffect(final FoldIntoAetherEffect effect) {
        super(effect);
    }

    @Override
    public FoldIntoAetherEffect copy() {
        return new FoldIntoAetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        StackObject stackObject = game.getStack().getStackObject(targetId);
        Player spellController = null;
        if (stackObject != null) {
            spellController = game.getPlayer(stackObject.getControllerId());
        }
        if (game.getStack().counter(targetId, source.getSourceId(), game)) {
            TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
            if (spellController != null
                    && target.canChoose(source.getSourceId(), spellController.getId(), game)
                    && spellController.chooseUse(Outcome.Neutral, "Put a creature card from your hand in play?", source, game)
                    && spellController.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    spellController.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
