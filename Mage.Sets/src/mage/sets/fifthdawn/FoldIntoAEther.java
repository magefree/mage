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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
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
public class FoldIntoAEther extends CardImpl<FoldIntoAEther> {

    public FoldIntoAEther(UUID ownerId) {
        super(ownerId, 31, "Fold into AEther", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "5DN";

        this.color.setBlue(true);

        // Counter target spell. If that spell is countered this way, its controller may put a creature card from his or her hand onto the battlefield.
        this.getSpellAbility().addEffect(new FoldIntoAEtherEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public FoldIntoAEther(final FoldIntoAEther card) {
        super(card);
    }

    @Override
    public FoldIntoAEther copy() {
        return new FoldIntoAEther(this);
    }
}

class FoldIntoAEtherEffect extends OneShotEffect<FoldIntoAEtherEffect> {

    public FoldIntoAEtherEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. If that spell is countered this way, its controller may put a creature card from his or her hand onto the battlefield";
    }

    public FoldIntoAEtherEffect(final FoldIntoAEtherEffect effect) {
        super(effect);
    }

    @Override
    public FoldIntoAEtherEffect copy() {
        return new FoldIntoAEtherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        Player player = null;
        if (stackObject != null) {
            player = game.getPlayer(stackObject.getControllerId());
        }
        if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
            TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
            if (player != null 
                    && player.chooseUse(Outcome.Neutral, "Put a creature card from your hand in play?", game)
                    && player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}
