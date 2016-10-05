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
package mage.cards.g;

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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Styxo/spjspj
 */
public class GrandMoffTarkin extends CardImpl {

    public GrandMoffTarkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beggining of each upkeep, destroy target creature that player controls unless that player pays 2 life. If a player pays life this way, draw a card.
        this.addAbility(new GrandMoffTarkinTriggeredAbility(new GrandMoffTarkinEffect(), false));
    }

    public GrandMoffTarkin(final GrandMoffTarkin card) {
        super(card);
    }

    @Override
    public GrandMoffTarkin copy() {
        return new GrandMoffTarkin(this);
    }
}

class GrandMoffTarkinTriggeredAbility extends TriggeredAbilityImpl {

    protected String text;

    public GrandMoffTarkinTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GrandMoffTarkinTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.text = text;
    }

    public GrandMoffTarkinTriggeredAbility(final GrandMoffTarkinTriggeredAbility ability) {
        super(ability);
        this.text = ability.text;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                this.getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature that player controls");
                filter.add(new ControllerIdPredicate(event.getPlayerId()));
                TargetPermanent target = new TargetPermanent(filter);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (text == null || text.isEmpty()) {
            return "At the beginning of each opponent's upkeep, " + super.getRule();
        }
        return text;
    }

    @Override
    public GrandMoffTarkinTriggeredAbility copy() {
        return new GrandMoffTarkinTriggeredAbility(this);
    }
}

class GrandMoffTarkinEffect extends OneShotEffect {

    public GrandMoffTarkinEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "destroy target creature that that player controls unless that player pays 2 life.  If a player pays life this way, draw a card";
    }

    public GrandMoffTarkinEffect(final GrandMoffTarkinEffect effect) {
        super(effect);
    }

    @Override
    public GrandMoffTarkinEffect copy() {
        return new GrandMoffTarkinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }

        Player player = game.getPlayer(targetCreature.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLife() > 2 && player.chooseUse(Outcome.Neutral, "Pay 2 life? If you don't, " + targetCreature.getName() + " will be destroyed", source, game)) {
            player.loseLife(2, game, false);
            game.informPlayers(player.getLogName() + " pays 2 life to prevent " + targetCreature.getName() + " being destroyed");
            Player sourceController = game.getPlayer(source.getControllerId());
            if (sourceController != null) {
                sourceController.drawCards(1, game);
            }

            return true;
        }

        targetCreature.destroy(source.getSourceId(), game, false);
        return true;
    }
}
