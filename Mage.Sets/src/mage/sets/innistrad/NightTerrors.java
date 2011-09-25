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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class NightTerrors extends CardImpl<NightTerrors> {

    public NightTerrors(UUID ownerId) {
        super(ownerId, 111, "Night Terrors", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "ISD";

        this.color.setBlack(true);

        // Target player reveals his or her hand. You choose a nonland card from it. Exile that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new NightTerrorsEffect());
    }

    public NightTerrors(final NightTerrors card) {
        super(card);
    }

    @Override
    public NightTerrors copy() {
        return new NightTerrors(this);
    }
}

class NightTerrorsEffect extends OneShotEffect<NightTerrorsEffect> {

    public NightTerrorsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player reveals his or her hand. You choose a nonland card from it. Exile that card";
    }

    public NightTerrorsEffect(final NightTerrorsEffect effect) {
        super(effect);
    }

    @Override
    public NightTerrorsEffect copy() {
        return new NightTerrorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Night Terrors", targetPlayer.getHand(), game);

            TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterNonlandCard("nonland card to exile"));
            target.setRequired(true);
            if (player.choose(Outcome.Exile, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    card.moveToExile(null, "", source.getId(), game);
                }
            }

            return true;
        }
        return false;
    }
}
