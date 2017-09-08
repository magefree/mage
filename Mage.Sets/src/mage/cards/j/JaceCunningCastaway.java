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
package mage.cards.j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.JaceCunningCastawayIllusionToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class JaceCunningCastaway extends CardImpl {

    public JaceCunningCastaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Jace");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card.
        this.addAbility(new LoyaltyAbility(new JaceCunningCastawayEffect1(), 1));

        // -2: Create a 2/2 blue Illusion creature token with "When this creature becomes the target of a spell, sacrifice it."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new JaceCunningCastawayIllusionToken()), -2));

        // -5: Create two tokens that are copies of Jace, Cunning Castaway, except they're not legendary.
        this.addAbility(new LoyaltyAbility(new JaceCunningCastawayCopyEffect(), -5));
    }

    public JaceCunningCastaway(final JaceCunningCastaway card) {
        super(card);
    }

    @Override
    public JaceCunningCastaway copy() {
        return new JaceCunningCastaway(this);
    }
}

class JaceCunningCastawayEffect1 extends OneShotEffect {

    public JaceCunningCastawayEffect1() {
        super(Outcome.DrawCard);
        this.staticText = "Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card";
    }

    public JaceCunningCastawayEffect1(final JaceCunningCastawayEffect1 effect) {
        super(effect);
    }

    @Override
    public JaceCunningCastawayEffect1 copy() {
        return new JaceCunningCastawayEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new JaceCunningCastawayDamageTriggeredAbility();
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class JaceCunningCastawayDamageTriggeredAbility extends DelayedTriggeredAbility {

    List<UUID> damagedPlayerIds = new ArrayList<>();

    public JaceCunningCastawayDamageTriggeredAbility() {
        super(new DrawDiscardControllerEffect(1, 1), Duration.EndOfTurn, false);
    }

    public JaceCunningCastawayDamageTriggeredAbility(final JaceCunningCastawayDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JaceCunningCastawayDamageTriggeredAbility copy() {
        return new JaceCunningCastawayDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (((DamagedPlayerEvent) event).isCombatDamage()) {
                Permanent creature = game.getPermanent(event.getSourceId());
                if (creature != null && creature.getControllerId().equals(controllerId)
                        && !damagedPlayerIds.contains(event.getTargetId())) {
                    damagedPlayerIds.add(event.getTargetId());
                    return true;
                }
            }
        }
        if (event.getType() == GameEvent.EventType.END_COMBAT_STEP_POST) {
            damagedPlayerIds.clear();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player this turn, draw a card, then discard a card";
    }
}

class JaceCunningCastawayCopyEffect extends OneShotEffect {

    JaceCunningCastawayCopyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Create two tokens that are copies of {this}, except they're not legendary";
    }

    JaceCunningCastawayCopyEffect(final JaceCunningCastawayCopyEffect effect) {
        super(effect);
    }

    @Override
    public JaceCunningCastawayCopyEffect copy() {
        return new JaceCunningCastawayCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, 2);
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
            effect.setIsntLegendary(true);
            return effect.apply(game, source);
        }
        return false;
    }
}
