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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AuguryAdept extends CardImpl<AuguryAdept> {

    public AuguryAdept(UUID ownerId) {
        super(ownerId, 137, "Augury Adept", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Kithkin");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Augury Adept deals combat damage to a player, reveal the top card of your library and put that card into your hand. You gain life equal to its converted mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AuguryAdeptEffect(), false));
    }

    public AuguryAdept(final AuguryAdept card) {
        super(card);
    }

    @Override
    public AuguryAdept copy() {
        return new AuguryAdept(this);
    }
}

class AuguryAdeptEffect extends OneShotEffect<AuguryAdeptEffect> {

    public AuguryAdeptEffect() {
        super(Outcome.GainLife);
        this.staticText = "reveal the top card of your library and put that card into your hand. You gain life equal to its converted mana cost";
    }

    public AuguryAdeptEffect(final AuguryAdeptEffect effect) {
        super(effect);
    }

    @Override
    public AuguryAdeptEffect copy() {
        return new AuguryAdeptEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || sourceCard == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = player.getLibrary().removeFromTop(game);
        if (card != null) {
            card.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);

            int cmc = card.getManaCost().convertedManaCost();
            if (cmc > 0) {
                player.gainLife(cmc, game);
            }
            cards.add(card);
            player.revealCards(sourceCard.getName(), cards, game);
            game.informPlayers(sourceCard.getName() + ": "+ player.getName() + " revealed " +card.getName() + " and gained " + cmc + " live");
        }
        return true;
    }
}
