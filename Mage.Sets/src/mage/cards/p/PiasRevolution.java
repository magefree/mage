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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public class PiasRevolution extends CardImpl {

    public PiasRevolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a nontoken artifact is put into your graveyard from the battlefield, return that card to your hand unless target opponent has Pia's Revolution deal 3 damage to him or her.
        Ability ability = new PiasRevolutionTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public PiasRevolution(final PiasRevolution card) {
        super(card);
    }

    @Override
    public PiasRevolution copy() {
        return new PiasRevolution(this);
    }
}

class PiasRevolutionReturnEffect extends OneShotEffect {

    public PiasRevolutionReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return that card to your hand unless target opponent has {this} deal 3 damage to him or her";
    }

    public PiasRevolutionReturnEffect(final PiasRevolutionReturnEffect effect) {
        super(effect);
    }

    @Override
    public PiasRevolutionReturnEffect copy() {
        return new PiasRevolutionReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID permanentId = (UUID) this.getValue("permanentId");
            Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
            if (permanent != null) {
                Player opponent = game.getPlayer(source.getFirstTarget());
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, new StringBuilder("Have Pia's Revolution deal 3 damage to you to prevent that ").append(permanent.getLogName()).append(" returns to ").append(controller.getLogName()).append("'s hand?").toString(), source, game)) {
                        opponent.damage(3, source.getId(), game, false, true);
                    } else {
                        if (game.getState().getZone(permanent.getId()).equals(Zone.GRAVEYARD)) {
                            controller.moveCards(game.getCard(permanentId), Zone.HAND, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class PiasRevolutionTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public PiasRevolutionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PiasRevolutionReturnEffect(), false);
    }

    public PiasRevolutionTriggeredAbility(PiasRevolutionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PiasRevolutionTriggeredAbility copy() {
        return new PiasRevolutionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone().equals(Zone.BATTLEFIELD) && zEvent.getToZone().equals(Zone.GRAVEYARD)) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && filter.match(permanent, sourceId, controllerId, game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("permanentId", event.getTargetId());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken artifact is put into your graveyard from the battlefield, " + super.getRule();
    }
}
