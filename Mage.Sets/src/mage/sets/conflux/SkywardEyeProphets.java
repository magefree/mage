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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class SkywardEyeProphets extends CardImpl<SkywardEyeProphets> {

    public SkywardEyeProphets(UUID ownerId) {
        super(ownerId, 125, "Skyward Eye Prophets", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{U}");
        this.expansionSetCode = "CON";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(VigilanceAbility.getInstance());
        // {tap}: Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SkywardEyeProphetsEffect(), new TapSourceCost()));
    }

    public SkywardEyeProphets(final SkywardEyeProphets card) {
        super(card);
    }

    @Override
    public SkywardEyeProphets copy() {
        return new SkywardEyeProphets(this);
    }
}

class SkywardEyeProphetsEffect extends OneShotEffect<SkywardEyeProphetsEffect> {

    public SkywardEyeProphetsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top card of your library. If it's a land card, put it onto the battlefield. Otherwise, put it into your hand";
    }

    public SkywardEyeProphetsEffect(final SkywardEyeProphetsEffect effect) {
        super(effect);
    }

    @Override
    public SkywardEyeProphetsEffect copy() {
        return new SkywardEyeProphetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getLibrary().size() > 0) {
            CardsImpl cards = new CardsImpl();
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                player.revealCards("Skyward Eye Prophets", cards, game);
                if (card.getCardType().contains(CardType.LAND)) {
                    card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                } else {
                    card.moveToZone(Zone.HAND, source.getId(), game, true);
                }
                return true;
            }
        }
        return false;
    }
}
