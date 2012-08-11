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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class PrinceOfThralls extends CardImpl<PrinceOfThralls> {

    public PrinceOfThralls(UUID ownerId) {
        super(ownerId, 182, "Prince of Thralls", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}{B}{B}{R}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Demon");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever a permanent an opponent controls is put into a graveyard, put that card onto the battlefield under your control unless that opponent pays 3 life.
        this.addAbility(new PrinceOfThrallsTriggeredAbility(new PrinceOfThrallsEffect()));
    }

    public PrinceOfThralls(final PrinceOfThralls card) {
        super(card);
    }

    @Override
    public PrinceOfThralls copy() {
        return new PrinceOfThralls(this);
    }
}

class PrinceOfThrallsTriggeredAbility extends TriggeredAbilityImpl<PrinceOfThrallsTriggeredAbility> {

    PrinceOfThrallsTriggeredAbility(Effect effect) {
        super(Constants.Zone.BATTLEFIELD, effect, false);
    }

    PrinceOfThrallsTriggeredAbility(final PrinceOfThrallsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrinceOfThrallsTriggeredAbility copy() {
        return new PrinceOfThrallsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Zone.GRAVEYARD) {
                if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                    Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                    if (game.getOpponents(this.getControllerId()).contains(permanent.getControllerId())) {
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent an opponent controls is put into a graveyard, " + super.getRule();
    }
}

class PrinceOfThrallsEffect extends OneShotEffect<PrinceOfThrallsEffect> {

    public PrinceOfThrallsEffect() {
        super(Constants.Outcome.Neutral);
        this.staticText = "put that card onto the battlefield under your control unless that opponent pays 3 life";
    }

    public PrinceOfThrallsEffect(final PrinceOfThrallsEffect effect) {
        super(effect);
    }

    @Override
    public PrinceOfThrallsEffect copy() {
        return new PrinceOfThrallsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        Permanent permanent = (Permanent) game.getLastKnownInformation(card.getId(), Constants.Zone.BATTLEFIELD);
        Player opponent = game.getPlayer(permanent.getControllerId());
        if (opponent != null && card != null && permanent != null && source.getControllerId() != null) {
            PayLifeCost cost = new PayLifeCost(3);
            if (opponent.chooseUse(Constants.Outcome.Neutral, cost.getText() + " or " + permanent.getName() + " comes back into the battlefield under opponents control", game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getId(), opponent.getId(), true)) {
                    return true;
                }
            }
            card.putOntoBattlefield(game, Zone.GRAVEYARD, id, source.getControllerId());
            return true;
        }
        return false;
    }
}
