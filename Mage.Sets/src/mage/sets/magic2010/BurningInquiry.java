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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class BurningInquiry extends CardImpl<BurningInquiry> {

    public BurningInquiry(UUID ownerId) {
        super(ownerId, 128, "Burning Inquiry", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "M10";

        this.color.setRed(true);

        // Each player draws three cards, then discards three cards at random.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(3));
        this.getSpellAbility().addEffect(new BurningInquiryEffect());
    }

    public BurningInquiry(final BurningInquiry card) {
        super(card);
    }

    @Override
    public BurningInquiry copy() {
        return new BurningInquiry(this);
    }
}

class BurningInquiryEffect extends OneShotEffect<BurningInquiryEffect> {

    public BurningInquiryEffect() {
        super(Outcome.Discard);
        this.staticText = "Each player discards three cards at random";
    }

    public BurningInquiryEffect(final BurningInquiryEffect effect) {
        super(effect);
    }

    @Override
    public BurningInquiryEffect copy() {
        return new BurningInquiryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : controller.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (int i = 0; i < 3; i++) {
                    Card card = player.getHand().getRandom(game);
                    if (card != null) {
                        player.discard(card, source, game);
                    }
                }
            }
        }
        return true;
    }
}
