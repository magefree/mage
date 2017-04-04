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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
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

/**
 *
 * @author jeffwadsworth
 */
public class BronzeBombshell extends CardImpl {

    public BronzeBombshell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add("Construct");
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When a player other than Bronze Bombshell's owner controls it, that player sacrifices it. If the player does, Bronze Bombshell deals 7 damage to him or her.
        this.addAbility(new LoseControlTriggeredAbility(new BronzeBombshellEffect(), false));

    }

    public BronzeBombshell(final BronzeBombshell card) {
        super(card);
    }

    @Override
    public BronzeBombshell copy() {
        return new BronzeBombshell(this);
    }
}

class LoseControlTriggeredAbility extends TriggeredAbilityImpl {

    public LoseControlTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public LoseControlTriggeredAbility(final LoseControlTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoseControlTriggeredAbility copy() {
        return new LoseControlTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return (event.getType() == GameEvent.EventType.LOST_CONTROL);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            Permanent sourcePermanent = game.getPermanent(event.getSourceId());
            if (sourcePermanent != null) {
                return !(sourcePermanent.getControllerId()).equals(sourcePermanent.getOwnerId());
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a player other than {this}'s owner controls it, " + super.getRule();
    }
}

class BronzeBombshellEffect extends OneShotEffect {

    public BronzeBombshellEffect() {
        super(Outcome.Damage);
        this.staticText = "that player sacrifices it. If the player does, {this} deals 7 damage to him or her.";
    }

    public BronzeBombshellEffect(final BronzeBombshellEffect effect) {
        super(effect);
    }

    @Override
    public BronzeBombshellEffect copy() {
        return new BronzeBombshellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent bronzeBombshell = game.getPermanent(source.getSourceId());
        if (bronzeBombshell != null) {
            Player newController = game.getPlayer(bronzeBombshell.getControllerId());
            if (newController != null) {
                if (bronzeBombshell.sacrifice(source.getId(), game)) {//sacrificed by the new controlling player
                    newController.damage(7, source.getSourceId(), game, false, true);//bronze bombshell does 7 damage to the controller
                    return true;
                }
            }
        }
        return false;
    }
}
