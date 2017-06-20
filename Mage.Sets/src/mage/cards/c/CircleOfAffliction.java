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

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class CircleOfAffliction extends CardImpl {

    public CircleOfAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // As Circle of Affliction enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        Ability ability = new CircleOfAfflictionTriggeredAbility();
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public CircleOfAffliction(final CircleOfAffliction card) {
        super(card);
    }

    @Override
    public CircleOfAffliction copy() {
        return new CircleOfAffliction(this);
    }
}

class CircleOfAfflictionTriggeredAbility extends TriggeredAbilityImpl {

    public CircleOfAfflictionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new LoseLifeTargetEffect(1), new GenericManaCost(1)), false);
        ((DoIfCostPaid) getEffects().get(0)).addEffect(new GainLifeEffect(1));
    }

    public CircleOfAfflictionTriggeredAbility(final CircleOfAfflictionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent circleOfAffliction = game.getPermanentOrLKIBattlefield(getSourceId());
        if (circleOfAffliction != null) {
            ObjectColor chosenColor = (ObjectColor) game.getState().getValue(circleOfAffliction.getId() + "_color");
            if (chosenColor != null) {
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource != null) {
                    if ( damageSource.getColor(game).shares(chosenColor) ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public CircleOfAfflictionTriggeredAbility copy() {
        return new CircleOfAfflictionTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a source of the chosen color deals damage to you, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.";
    }
}