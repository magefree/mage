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
package mage.sets.eventide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 *
 */
public class PhosphorescentFeast extends CardImpl<PhosphorescentFeast> {

    public PhosphorescentFeast(UUID ownerId) {
        super(ownerId, 72, "Phosphorescent Feast", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}{G}{G}");
        this.expansionSetCode = "EVE";

        this.color.setGreen(true);

        // Chroma - Reveal any number of cards in your hand. You gain 2 life for each green mana symbol in those cards' mana costs.
        Effect effect = new PhosphorescentFeastEffect();
        effect.setText("<i>Chroma</i> - Reveal any number of cards in your hand. You gain 2 life for each green mana symbol in those cards' mana costs.");
        this.getSpellAbility().addEffect(effect);

    }

    public PhosphorescentFeast(final PhosphorescentFeast card) {
        super(card);
    }

    @Override
    public PhosphorescentFeast copy() {
        return new PhosphorescentFeast(this);
    }
}

class PhosphorescentFeastEffect extends OneShotEffect<PhosphorescentFeastEffect> {

    public PhosphorescentFeastEffect() {
        super(Outcome.GainLife);
    }

    public PhosphorescentFeastEffect(final PhosphorescentFeastEffect effect) {
        super(effect);
    }

    @Override
    public PhosphorescentFeastEffect copy() {
        return new PhosphorescentFeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int chroma = 0;
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        if (player.getHand().count(new FilterCard(), game) > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterCard());
            if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                for (UUID uuid : target.getTargets()) {
                    cards.add(player.getHand().get(uuid, game));
                }
                player.revealCards("cards", cards, game);
                for (Card card : cards.getCards(game)) {
                    chroma += card.getManaCost().getMana().getGreen();
                }
                player.gainLife(chroma * 2, game);
            }
        }
        return true;
    }
}
