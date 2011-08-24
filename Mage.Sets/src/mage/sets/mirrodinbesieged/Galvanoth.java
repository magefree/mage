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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Galvanoth extends CardImpl<Galvanoth> {

    public Galvanoth(UUID ownerId) {
        super(ownerId, 62, "Galvanoth", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GalvanothEffect(), TargetController.YOU, true));
    }

    public Galvanoth(final Galvanoth card) {
        super(card);
    }

    @Override
    public Galvanoth copy() {
        return new Galvanoth(this);
    }
}

class GalvanothEffect extends OneShotEffect<GalvanothEffect> {

    public GalvanothEffect() {
        super(Outcome.PlayForFree);
        staticText = "look at the top card of your library. If it's an instant or sorcery card, you may cast it without paying its mana cost";
    }

    public GalvanothEffect(final GalvanothEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards("Galvanoth", cards, game);

            if (card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY)) {
                String message = "Cast " + card.getName() + " without paying its mana cost?";
                if (player.chooseUse(Outcome.PlayForFree, message, game)) {
                    player.getLibrary().removeFromTop(game);
                    player.cast(card.getSpellAbility(), game, true);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public GalvanothEffect copy() {
        return new GalvanothEffect(this);
    }
}
