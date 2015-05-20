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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;

/**
 *
 * @author Plopman
 */
public class CompulsiveResearch extends CardImpl {

    public CompulsiveResearch(UUID ownerId) {
        super(ownerId, 40, "Compulsive Research", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "RAV";


        // Target player draws three cards. Then that player discards two cards unless he or she discards a land card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
        this.getSpellAbility().addEffect(new CompulsiveResearchDiscardEffect());
    }

    public CompulsiveResearch(final CompulsiveResearch card) {
        super(card);
    }

    @Override
    public CompulsiveResearch copy() {
        return new CompulsiveResearch(this);
    }
}
class CompulsiveResearchDiscardEffect extends OneShotEffect {

    public CompulsiveResearchDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Then that player discards two cards unless he or she discards a land card";
    }

    public CompulsiveResearchDiscardEffect(final CompulsiveResearchDiscardEffect effect) {
        super(effect);
    }

    @Override
    public CompulsiveResearchDiscardEffect copy() {
        return new CompulsiveResearchDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && !targetPlayer.getHand().isEmpty()) {
            TargetDiscard target = new TargetDiscard(targetPlayer.getId());
            targetPlayer.choose(Outcome.Discard, target, source.getSourceId(), game);
            Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                targetPlayer.discard(card, source, game);
                if (!card.getCardType().contains(CardType.LAND) && !targetPlayer.getHand().isEmpty()) {
                    targetPlayer.discard(1, false, source, game);
                }
                return true;
            }            
        }
        return false;
    }
}
