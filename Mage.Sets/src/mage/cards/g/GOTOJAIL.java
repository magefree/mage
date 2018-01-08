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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public class GOTOJAIL extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public GOTOJAIL(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // When GO TO JAIL enters the battlefield, exile target creature an opponent controls until GO TO JAIL leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoToJailExileEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // At the beginning of the upkeep of the exiled card's owner, that player rolls two six-sided dice. If he or she rolls doubles, sacrifice GO TO JAIL.
        this.addAbility(new GoToJailTriggeredAbility());
    }

    public GOTOJAIL(final GOTOJAIL card) {
        super(card);
    }

    @Override
    public GOTOJAIL copy() {
        return new GOTOJAIL(this);
    }
}

class GoToJailExileEffect extends OneShotEffect {

    public GoToJailExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield.";
    }

    public GoToJailExileEffect(final GoToJailExileEffect effect) {
        super(effect);
    }

    @Override
    public GoToJailExileEffect copy() {
        return new GoToJailExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));

        // If GO TO JAIL leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null && targetPermanent != null) {
            Player controller = game.getPlayer(targetPermanent.getControllerId());
            if (controller != null) {
                game.getState().setValue(permanent.getId() + ChooseOpponentEffect.VALUE_KEY, controller.getId());
                return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName()).apply(game, source);
            }
        }
        return false;
    }
}

class GoToJailTriggeredAbility extends TriggeredAbilityImpl {

    public GoToJailTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GoToJailUpkeepEffect(), false);
    }

    public GoToJailTriggeredAbility(final GoToJailTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoToJailTriggeredAbility copy() {
        return new GoToJailTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals((UUID) game.getState().getValue(this.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY));
    }

    @Override
    public String getRule() {
        return "At the beginning of the chosen player's upkeep, " + super.getRule();
    }
}

class GoToJailUpkeepEffect extends OneShotEffect {

    public GoToJailUpkeepEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "that player rolls two six-sided dice. If he or she rolls doubles, sacrifice {this}";
    }

    public GoToJailUpkeepEffect(final GoToJailUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public GoToJailUpkeepEffect copy() {
        return new GoToJailUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);


        if (sourceObject != null && sourceObject instanceof Permanent && permanent != null) {
            UUID opponentId = (UUID) game.getState().getValue(sourceObject.getId().toString() + ChooseOpponentEffect.VALUE_KEY);
            Player opponent = game.getPlayer(opponentId);

            if (opponent != null) {
                int thisRoll = opponent.rollDice(game, 6);
                int thatRoll = opponent.rollDice(game, 6);
                if (thisRoll == thatRoll) {
                    return permanent.sacrifice(source.getSourceId(), game);
                }
            }
        }
        return false;
    }
}
