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
package mage.cards.p;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author anonymous
 */
public class PsychoticEpisode extends CardImpl {

    public PsychoticEpisode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Target player reveals his or her hand and the top card of his or her library. You choose a card revealed this way. That player puts the chosen card on the bottom of his or her library.
        this.getSpellAbility().addEffect(new PsychoticEpisodeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // Madness {1}{B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl("{1}{B}")));
    }

    public PsychoticEpisode(final PsychoticEpisode card) {
        super(card);
    }

    @Override
    public PsychoticEpisode copy() {
        return new PsychoticEpisode(this);
    }
}


class PsychoticEpisodeEffect extends OneShotEffect {

    PsychoticEpisodeEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals his or her hand and the top card of his or her library. You choose a card revealed this way. That player puts the chosen card on the bottom of his or her library.";
    }

    PsychoticEpisodeEffect(final PsychoticEpisodeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && controller != null && sourceObject != null) {
            TargetCard targetCard = new TargetCard(Zone.ALL, new FilterCard());
            targetCard.setRequired(true);
            Cards options = player.getHand().copy();
            Card topdeck = player.getLibrary().getFromTop(game);
            options.add(topdeck);
            controller.lookAtCards("Top of Library (Psychotic Episode)", topdeck, game);
            if (controller.choose(Outcome.Discard, options, targetCard, game)) {
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    player.revealCards(sourceObject.getIdName(), cards, game);
                    player.putCardsOnBottomOfLibrary(cards, game, source, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PsychoticEpisodeEffect copy() {
        return new PsychoticEpisodeEffect(this);
    }
}
