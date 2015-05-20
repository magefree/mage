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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public class AgonizingMemories extends CardImpl {

    public AgonizingMemories(UUID ownerId) {
        super(ownerId, 117, "Agonizing Memories", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "7ED";


        // Look at target player's hand and choose two cards from it. Put them on top of that player's library in any order.
        this.getSpellAbility().addEffect(new AgonizingMemoriesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public AgonizingMemories(final AgonizingMemories card) {
        super(card);
    }

    @Override
    public AgonizingMemories copy() {
        return new AgonizingMemories(this);
    }
}

class AgonizingMemoriesEffect extends OneShotEffect {

    public AgonizingMemoriesEffect() {
        super(Outcome.Discard);
        this.staticText = "Look at target player's hand and choose two cards from it. Put them on top of that player's library in any order.";
    }

    public AgonizingMemoriesEffect(final AgonizingMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public AgonizingMemoriesEffect copy() {
        return new AgonizingMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            chooseCardInHandAndPutOnTopOfLibrary(game, source, you, targetPlayer);
            chooseCardInHandAndPutOnTopOfLibrary(game, source, you, targetPlayer);
            return true;
        }
        return false;
    }
    
    private void chooseCardInHandAndPutOnTopOfLibrary(Game game, Ability source, Player you, Player targetPlayer) {
        if (targetPlayer.getHand().size() > 0) {
            TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the top of library (last chosen will be on top)"));
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    targetPlayer.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, true, false);
                }
            }
        }
    }
}
