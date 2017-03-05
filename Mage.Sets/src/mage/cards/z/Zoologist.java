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
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Zoologist extends CardImpl {

    public Zoologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}{G}, {tap}: Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZoologistEffect(), new ManaCostsImpl("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public Zoologist(final Zoologist card) {
        super(card);
    }

    @Override
    public Zoologist copy() {
        return new Zoologist(this);
    }
}

class ZoologistEffect extends OneShotEffect {

    public ZoologistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard";
    }

    public ZoologistEffect(final ZoologistEffect effect) {
        super(effect);
    }

    @Override
    public ZoologistEffect copy() {
        return new ZoologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            if (card != null) {
                if (card.isCreature()) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}
