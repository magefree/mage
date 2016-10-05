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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class OathOfJace extends CardImpl {

    public OathOfJace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.supertype.add("Legendary");

        // When Oath of Jace enters the battlefield, draw three cards, then discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(3, 2), false));

        // At the beginning of your upkeep, scry X, where X is the number of planeswalkers you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new OathOfJaceEffect(), TargetController.YOU, false));

    }

    public OathOfJace(final OathOfJace card) {
        super(card);
    }

    @Override
    public OathOfJace copy() {
        return new OathOfJace(this);
    }
}

class OathOfJaceEffect extends OneShotEffect {

    public OathOfJaceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "scry X, where X is the number of planeswalkers you control";
    }

    public OathOfJaceEffect(final OathOfJaceEffect effect) {
        super(effect);
    }

    @Override
    public OathOfJaceEffect copy() {
        return new OathOfJaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int planeswalker = game.getBattlefield().countAll(new FilterPlaneswalkerPermanent(), source.getControllerId(), game);
            if (planeswalker > 0) {
                controller.scry(planeswalker, source, game);
            }
            return true;
        }
        return false;
    }
}
