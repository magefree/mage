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

package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class HideSeek extends SplitCard {

    public HideSeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{W}","{W}{B}",false);

        // Hide
        // Put target artifact or enchantment on the bottom of its owner's library.
        getLeftHalfCard().getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(new FilterArtifactOrEnchantmentPermanent()));

        // Seek
        // Search target opponent's library for a card and exile it. You gain life equal to its converted mana cost. Then that player shuffles his or her library..
        getRightHalfCard().getSpellAbility().addEffect(new SeekEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());

    }

    public HideSeek(final HideSeek card) {
        super(card);
    }

    @Override
    public HideSeek copy() {
        return new HideSeek(this);
    }
}

class SeekEffect extends OneShotEffect {

    public SeekEffect() {
        super(Outcome.GainLife);
        staticText = "Search target opponent's library for a card and exile it. You gain life equal to its converted mana cost. Then that player shuffles his or her library";
    }

    public SeekEffect(final SeekEffect effect) {
        super(effect);
    }

    @Override
    public SeekEffect copy() {
        return new SeekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && opponent != null) {
            if (opponent.getLibrary().size() > 0) {
                TargetCardInLibrary target = new TargetCardInLibrary();
                if (player.searchLibrary(target, game, opponent.getId())) {
                    UUID targetId = target.getFirstTarget();
                    Card card = opponent.getLibrary().remove(targetId, game);
                    if (card != null) {
                        player.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY, true);
                        int cmc = card.getConvertedManaCost();
                        if (cmc > 0) {
                            player.gainLife(cmc, game);
                        }
                    }
                }
            }
            opponent.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
