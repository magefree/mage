/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TezzeretAgentOfBolas extends CardImpl<TezzeretAgentOfBolas> {

    public TezzeretAgentOfBolas(UUID ownerId) {
        super(ownerId, 97, "Tezzeret, Agent of Bolas", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Tezzeret");
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), ""));

        this.addAbility(new LoyaltyAbility(new TezzeretAgentOfBolasEffect1(), 1));

        LoyaltyAbility ability1 = new LoyaltyAbility(new BecomesCreatureTargetEffect(new ArtifactCreatureToken(), "", Duration.EndOfGame), -1);
        ability1.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability1);

        LoyaltyAbility ability2 = new LoyaltyAbility(new TezzeretAgentOfBolasEffect2(), -4);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);

    }

    public TezzeretAgentOfBolas(final TezzeretAgentOfBolas card) {
        super(card);
    }

    @Override
    public TezzeretAgentOfBolas copy() {
        return new TezzeretAgentOfBolas(this);
    }

}

class TezzeretAgentOfBolasEffect1 extends OneShotEffect<TezzeretAgentOfBolasEffect1> {

    public TezzeretAgentOfBolasEffect1() {
        super(Outcome.DrawCard);
        staticText = "Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public TezzeretAgentOfBolasEffect1(final TezzeretAgentOfBolasEffect1 effect) {
        super(effect);
    }

    @Override
    public TezzeretAgentOfBolasEffect1 copy() {
        return new TezzeretAgentOfBolasEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(Zone.PICK);
        boolean artifactFound = false;
        int count = Math.min(player.getLibrary().size(), 5);
        for (int i = 0; i < count; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                game.setZone(card.getId(), Zone.PICK);
                if (card.getCardType().contains(CardType.ARTIFACT)) {
                    artifactFound = true;
                }
            }
        }
        player.lookAtCards("Tezzeret, Agent of Bolas", cards, game);

        if (artifactFound && player.chooseUse(Outcome.DrawCard, "Do you wish to reveal an artifact card and put it into your hand?", game)) {
            TargetCard target = new TargetCard(Zone.PICK, new FilterArtifactCard("artifact card to reveal and put into your hand"));
            if (player.choose(Outcome.DrawCard, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.HAND, source.getId(), game, false);

                    Cards reveal = new CardsImpl(Zone.OUTSIDE);
                    reveal.add(card);
                    player.revealCards("Tezzeret, Agent of Bolas", reveal, game);
                }
            }
        }

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }
}

class ArtifactCreatureToken extends Token {

    public ArtifactCreatureToken() {
        super("", "5/5 artifact creature");
        this.cardType.add(CardType.CREATURE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
    }
}

class TezzeretAgentOfBolasEffect2 extends OneShotEffect<TezzeretAgentOfBolasEffect2> {

    final static FilterControlledPermanent filter = new FilterControlledPermanent("artifacts");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public TezzeretAgentOfBolasEffect2() {
        super(Outcome.DrawCard);
        staticText = "Target player loses X life and you gain X life, where X is twice the number of artifacts you control";
    }

    public TezzeretAgentOfBolasEffect2(final TezzeretAgentOfBolasEffect2 effect) {
        super(effect);
    }

    @Override
    public TezzeretAgentOfBolasEffect2 copy() {
        return new TezzeretAgentOfBolasEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue value = new PermanentsOnBattlefieldCount(filter);
        int count = value.calculate(game, source) * 2;

        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.loseLife(count, game);
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(count, game);
        }
        return true;
    }

}
