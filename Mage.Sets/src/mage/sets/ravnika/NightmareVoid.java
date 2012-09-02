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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public class NightmareVoid extends CardImpl<NightmareVoid> {

    public NightmareVoid(UUID ownerId) {
        super(ownerId, 100, "Nightmare Void", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "RAV";

        this.color.setBlack(true);

        // Target player reveals his or her hand. You choose a card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new NightmareVoidEffect());
        // Dredge 2
        this.addAbility(new DredgeAbility(2));
    }

    public NightmareVoid(final NightmareVoid card) {
        super(card);
    }

    @Override
    public NightmareVoid copy() {
        return new NightmareVoid(this);
    }
}

class NightmareVoidEffect extends OneShotEffect<NightmareVoidEffect> {

    public NightmareVoidEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals his or her hand. You choose a card from it. That player discards that card";
    }

    public NightmareVoidEffect(final NightmareVoidEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.revealCards("Nightmare Void", player.getHand(), game);

            Player you = game.getPlayer(source.getControllerId());
            TargetCard target = new TargetCard(Zone.PICK, new FilterCard());
            target.setRequired(true);
            if (you != null && you.choose(Outcome.Benefit, player.getHand(), target, game)) {
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    return player.discard(card, source, game);
                }
            }
        }
        return false;
    }

    @Override
    public NightmareVoidEffect copy() {
        return new NightmareVoidEffect(this);
    }
}
