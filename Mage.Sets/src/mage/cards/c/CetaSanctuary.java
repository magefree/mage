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
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Pete Rossi
 */
public class CetaSanctuary extends CardImpl {

    public CetaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. If you control a red permanent and a green permanent, instead draw two cards, then discard a card.
        this.addAbility(new CetaSanctuaryTriggeredAbility());
    }

    public CetaSanctuary(final CetaSanctuary card) {
        super(card);
    }

    @Override
    public CetaSanctuary copy() {
        return new CetaSanctuary(this);
    }
}

class CetaSanctuaryEffect extends OneShotEffect {

    public CetaSanctuaryEffect() {
        super(Outcome.DrawCard);
    }

    public CetaSanctuaryEffect(final CetaSanctuaryEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        int red = 0;
        int green = 0;

        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                ObjectColor color = permanent.getColor(game);
                if (color.isRed()) { red = 1; }
                if (color.isGreen()) { green = 1; }

                if (red == 1 && green == 1) {
                    break;
                }
            }
        }

        if (red != 0 || green != 0) {
            controller.drawCards((red+green), game);
            controller.discard(1, false, source, game);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CetaSanctuaryEffect copy() { return new CetaSanctuaryEffect(this); }
}

class CetaSanctuaryTriggeredAbility extends TriggeredAbilityImpl {

    public CetaSanctuaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CetaSanctuaryEffect());
    }

    public CetaSanctuaryTriggeredAbility(final CetaSanctuaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CetaSanctuaryTriggeredAbility copy() {
        return new CetaSanctuaryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(this.getControllerId())) {
                ObjectColor color = permanent.getColor(game);
                if (color.isRed() || color.isGreen()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. If you control a red permanent and a green permanent, instead draw two cards, then discard a card.";
    }
}

