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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class Bioplasm extends CardImpl {

    public Bioplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Bioplasm attacks, exile the top card of your library. If it's a creature card, Bioplasm gets +X/+Y until end of turn, where X is the exiled creature card's power and Y is its toughness.
        this.addAbility(new AttacksTriggeredAbility(new BioplasmEffect(), false));
    }

    public Bioplasm(final Bioplasm card) {
        super(card);
    }

    @Override
    public Bioplasm copy() {
        return new Bioplasm(this);
    }
}

class BioplasmEffect extends OneShotEffect {

    BioplasmEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top card of your library. If it's a creature card, {this} gets +X/+Y until end of turn, where X is the exiled creature card's power and Y is its toughness";
    }

    BioplasmEffect(final BioplasmEffect effect) {
        super(effect);
    }

    @Override
    public BioplasmEffect copy() {
        return new BioplasmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (library == null || !library.hasCards()) {
            return false;
        }
        Card card = library.getFromTop(game);
        if (card == null) {
            return false;
        }
        if (player.moveCards(card, Zone.EXILED, source, game) && card.isCreature()) {
            game.addEffect(new BoostSourceEffect(card.getPower().getValue(), card.getToughness().getValue(), Duration.EndOfTurn), source);
        }
        return true;
    }
}
