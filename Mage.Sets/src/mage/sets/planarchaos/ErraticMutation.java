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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;



/**
 *
 * @author LevelX2
 */
public class ErraticMutation extends CardImpl<ErraticMutation> {

    public ErraticMutation(UUID ownerId) {
        super(ownerId, 41, "Erratic Mutation", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "PLC";

        this.color.setBlue(true);

        // Choose target creature. Reveal cards from the top of your library until you reveal a nonland card. That creature gets +X/-X until end of turn, where X is that card's converted mana cost. Put all cards revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
        this.getSpellAbility().addEffect(new ErraticMutationEffect());

    }

    public ErraticMutation(final ErraticMutation card) {
        super(card);
    }

    @Override
    public ErraticMutation copy() {
        return new ErraticMutation(this);
    }
}

class ErraticMutationEffect extends OneShotEffect<ErraticMutationEffect> {

    public ErraticMutationEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose target creature. Reveal cards from the top of your library until you reveal a nonland card. That creature gets +X/-X until end of turn, where X is that card's converted mana cost. Put all cards revealed this way on the bottom of your library in any order";
    }

    public ErraticMutationEffect(final ErraticMutationEffect effect) {
        super(effect);
    }

    @Override
    public ErraticMutationEffect copy() {
        return new ErraticMutationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            CardsImpl cards = new CardsImpl();
            Library library = player.getLibrary();
            Card card = null;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            } while (library.size() > 0 && card != null && card.getCardType().contains(CardType.LAND));
            // reveal cards
            if (!cards.isEmpty()) {
                player.revealCards("Erratic Mutation", cards, game);
            }
            // the nonland card
            int boostValue = card.getManaCost().convertedManaCost();
            // unboost target
            ContinuousEffect effect = new BoostTargetEffect(boostValue, boostValue * -1, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
            game.addEffect(effect, source);
            // put the cards on the bottom of the library in any order
            return player.putCardsOnBottomOfLibrary(cards, game, source, true);
        }
        return false;
    }
}
