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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public class LlanowarEmpath extends CardImpl {

    public LlanowarEmpath(UUID ownerId) {
        super(ownerId, 130, "Llanowar Empath", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Elf");
        this.subtype.add("Shaman");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Llanowar Empath enters the battlefield, scry 2, then reveal the top card of your library. If it's a creature card, put it into your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(2));
        ability.addEffect(new LlanowarEmpathEffect());
        this.addAbility(ability);
    }

    public LlanowarEmpath(final LlanowarEmpath card) {
        super(card);
    }

    @Override
    public LlanowarEmpath copy() {
        return new LlanowarEmpath(this);
    }
}

class LlanowarEmpathEffect extends OneShotEffect {

    public LlanowarEmpathEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library. If it's a creature card, put it into your hand.";
    }

    public LlanowarEmpathEffect(final LlanowarEmpathEffect effect) {
        super(effect);
    }

    @Override
    public LlanowarEmpathEffect copy() {
        return new LlanowarEmpathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if(controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = controller.getLibrary().getFromTop(game);
        if(card != null) {
            cards.add(card);
            controller.revealCards(sourceObject.getName(), cards, game);
            if(card.getCardType().contains(CardType.CREATURE)) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
        }
        return true;
    }
}
