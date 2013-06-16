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
package mage.sets.conflux;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Progenitus extends CardImpl<Progenitus> {

    public Progenitus(UUID ownerId) {
        super(ownerId, 121, "Progenitus", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}");
        this.expansionSetCode = "CON";
        this.supertype.add("Legendary");
        this.subtype.add("Hydra");
        this.subtype.add("Avatar");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Protection from everything
        this.addAbility(new ProgenitusProtectionAbility());
        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new ProgenitusEffect()));
    }

    public Progenitus(final Progenitus card) {
        super(card);
    }

    @Override
    public Progenitus copy() {
        return new Progenitus(this);
    }
}

class ProgenitusProtectionAbility extends StaticAbility<ProgenitusProtectionAbility> {

    public ProgenitusProtectionAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public ProgenitusProtectionAbility(final ProgenitusProtectionAbility ability) {
        super(ability);
    }

    @Override
    public ProgenitusProtectionAbility copy() {
        return new ProgenitusProtectionAbility(this);
    }

    @Override
    public String getRule() {
        return "Protection from everything";
    }

    public boolean canTarget(MageObject source, Game game) {
        return false;
    }
}

class ProgenitusEffect extends OneShotEffect<ProgenitusEffect> {

    public ProgenitusEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal {this} and shuffle it into its owner's library instead";
    }

    public ProgenitusEffect(final ProgenitusEffect effect) {
        super(effect);
    }

    @Override
    public ProgenitusEffect copy() {
        return new ProgenitusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                player.revealCards("Progenitus", cards, game);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                player.shuffleLibrary(game);
                return true;
            }
        }
        return false;
    }
}
