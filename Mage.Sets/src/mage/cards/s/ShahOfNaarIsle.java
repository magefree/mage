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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ShahOfNaarIsle extends CardImpl {

    public ShahOfNaarIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Echo {0}
        this.addAbility(new EchoAbility("{0}"));

        // When Shah of Naar Isle's echo cost is paid, each opponent may draw up to three cards.
        this.addAbility(new ShahOfNaarIsleTriggeredAbility());
    }

    public ShahOfNaarIsle(final ShahOfNaarIsle card) {
        super(card);
    }

    @Override
    public ShahOfNaarIsle copy() {
        return new ShahOfNaarIsle(this);
    }
}

class ShahOfNaarIsleTriggeredAbility extends TriggeredAbilityImpl {

    public ShahOfNaarIsleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ShahOfNaarIsleEffect(), false);
    }

    public ShahOfNaarIsleTriggeredAbility(final ShahOfNaarIsleTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ECHO_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getSourceId());
    }

    @Override
    public ShahOfNaarIsleTriggeredAbility copy() {
        return new ShahOfNaarIsleTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this}'s echo cost is paid, " + super.getRule();
    }
}

class ShahOfNaarIsleEffect extends OneShotEffect {

    public ShahOfNaarIsleEffect() {
        super(Outcome.DrawCard);
        this.staticText = "each opponent may draw up to three cards";
    }

    public ShahOfNaarIsleEffect(final ShahOfNaarIsleEffect effect) {
        super(effect);
    }

    @Override
    public ShahOfNaarIsleEffect copy() {
        return new ShahOfNaarIsleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    int number = opponent.getAmount(0, 3, "Draw how many cards?", game);
                    opponent.drawCards(number, game);
                }
            }
            return true;
        }
        return false;
    }
}
