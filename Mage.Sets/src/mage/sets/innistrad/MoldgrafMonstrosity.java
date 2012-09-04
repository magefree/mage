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

import java.util.Random;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class MoldgrafMonstrosity extends CardImpl<MoldgrafMonstrosity> {

    public MoldgrafMonstrosity(UUID ownerId) {
        super(ownerId, 194, "Moldgraf Monstrosity", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Insect");

        this.color.setGreen(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        this.addAbility(TrampleAbility.getInstance());
        // When Moldgraf Monstrosity dies, exile it, then return two creature cards at random from your graveyard to the battlefield.
        DiesTriggeredAbility ability = new DiesTriggeredAbility(new ExileSourceEffect());
        ability.addEffect(new MoldgrafMonstrosityEffect());
        this.addAbility(ability);
    }

    public MoldgrafMonstrosity(final MoldgrafMonstrosity card) {
        super(card);
    }

    @Override
    public MoldgrafMonstrosity copy() {
        return new MoldgrafMonstrosity(this);
    }
}

class MoldgrafMonstrosityEffect extends OneShotEffect<MoldgrafMonstrosityEffect> {

    public MoldgrafMonstrosityEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "then return two creature cards at random from your graveyard to the battlefield";
    }

    public MoldgrafMonstrosityEffect(final MoldgrafMonstrosityEffect effect) {
        super(effect);
    }

    @Override
    public MoldgrafMonstrosityEffect copy() {
        return new MoldgrafMonstrosityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean returned = false;
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Set<Card> cards = player.getGraveyard().getCards(new FilterCreatureCard("creature cards"), game);

            for (int i = 0; i < 2; i++) {
                Card card = getRandomCard(cards);
                if (card != null) {
                    returned |= card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId());
                    cards.remove(card);
                }
            }
        }
        return returned;
    }

    private Card getRandomCard(Set<Card> cards) {
        int i = 0;
        int pick = new Random().nextInt(cards.size());
        for (Card card : cards) {
            if (i == pick) {
                return card;
            }
            i = i + 1;
        }
        return null;
    }
}
