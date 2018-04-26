/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.m;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MyrToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MyrBattlesphere extends CardImpl {

    public MyrBattlesphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.MYR);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // When Myr Battlesphere enters the battlefield, create four 1/1 colorless Myr artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MyrToken(), 4), false));

        // Whenever Myr Battlesphere attacks, you may tap X untapped Myr you control. If you do, Myr Battlesphere gets +X/+0 until end of turn and deals X damage to defending player.
        this.addAbility(new MyrBattlesphereTriggeredAbility());

    }

    public MyrBattlesphere(final MyrBattlesphere card) {
        super(card);
    }

    @Override
    public MyrBattlesphere copy() {
        return new MyrBattlesphere(this);
    }

}

class MyrBattlesphereTriggeredAbility extends TriggeredAbilityImpl {

    public MyrBattlesphereTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MyrBattlesphereEffect(), true);
    }

    public MyrBattlesphereTriggeredAbility(final MyrBattlesphereTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MyrBattlesphereTriggeredAbility copy() {
        return new MyrBattlesphereTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(event.getSourceId());
        if (source != null && source.getId() == this.getSourceId()) {
            UUID defenderId = game.getCombat().getDefenderId(event.getSourceId());
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defenderId));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks, you may tap X untapped Myr you control. If you do, {this} gets +X/+0 until end of turn and deals X damage to the player or planeswalker it’s attacking.";
    }
}

class MyrBattlesphereEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Myr you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.MYR));
    }

    public MyrBattlesphereEffect() {
        super(Outcome.Damage);
        staticText = "you may tap X untapped Myr you control. If you do, {this} gets +X/+0 until end of turn and deals X damage to the player or planeswalker it’s attacking.";
    }

    public MyrBattlesphereEffect(final MyrBattlesphereEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent myr = game.getPermanentOrLKIBattlefield(source.getSourceId());
            int tappedAmount = 0;
            TargetPermanent target = new TargetPermanent(0, 1, filter, true);
            while (true && controller.canRespond()) {
                target.clearChosen();
                if (target.canChoose(source.getControllerId(), game)) {
                    Map<String, Serializable> options = new HashMap<>();
                    options.put("UI.right.btn.text", "Myr tapping complete");
                    controller.choose(outcome, target, source.getControllerId(), game, options);
                    if (!target.getTargets().isEmpty()) {
                        UUID creature = target.getFirstTarget();
                        if (creature != null) {
                            game.getPermanent(creature).tap(game);
                            tappedAmount++;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (tappedAmount > 0) {
                game.informPlayers(new StringBuilder(controller.getLogName()).append(" taps ").append(tappedAmount).append(" Myrs").toString());
                // boost effect
                game.addEffect(new BoostSourceEffect(tappedAmount, 0, Duration.EndOfTurn), source);
                // damage to defender
                return game.damagePlayerOrPlaneswalker(targetPointer.getFirst(game, source), tappedAmount, myr.getId(), game, false, true) > 0;
            }
            return true;
        }
        return false;
    }

    @Override
    public MyrBattlesphereEffect copy() {
        return new MyrBattlesphereEffect(this);
    }

}
