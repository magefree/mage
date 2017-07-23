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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ThrasiosTritonHero extends CardImpl {

    public ThrasiosTritonHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {4}: Scry 1, then reveal the top card of your library. If it's a land card, put it onto the battlefield tapped. Otherwise, draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new GenericManaCost(4));
        ability.addEffect(new ThrasiosTritonHeroEffect());
        this.addAbility(ability);
        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public ThrasiosTritonHero(final ThrasiosTritonHero card) {
        super(card);
    }

    @Override
    public ThrasiosTritonHero copy() {
        return new ThrasiosTritonHero(this);
    }
}

class ThrasiosTritonHeroEffect extends OneShotEffect {

    public ThrasiosTritonHeroEffect() {
        super(Outcome.DrawCard);
        this.staticText = "then reveal the top card of your library. If it's a land card, put it onto the battlefield tapped. Otherwise, draw a card";
    }

    public ThrasiosTritonHeroEffect(final ThrasiosTritonHeroEffect effect) {
        super(effect);
    }

    @Override
    public ThrasiosTritonHeroEffect copy() {
        return new ThrasiosTritonHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null || controller == null) {
            return false;
        }
        if (controller.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);
            controller.revealCards(sourceObject.getName(), cards, game);
            if (card.isLand()) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            } else {
                controller.drawCards(1, game);
            }
        }
        return true;
    }
}
