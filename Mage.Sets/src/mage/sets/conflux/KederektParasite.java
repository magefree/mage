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
package mage.sets.conflux;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class KederektParasite extends CardImpl<KederektParasite> {

    public KederektParasite(UUID ownerId) {
        super(ownerId, 48, "Kederekt Parasite", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "CON";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent draws a card, if you control a red permanent, you may have Kederekt Parasite deal 1 damage to that player.
        this.addAbility(new KederektParasiteTriggeredAbility());
    }

    public KederektParasite(final KederektParasite card) {
        super(card);
    }

    @Override
    public KederektParasite copy() {
        return new KederektParasite(this);
    }
}

class KederektParasiteTriggeredAbility extends TriggeredAbilityImpl<KederektParasiteTriggeredAbility> {

    KederektParasiteTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(1, false, "opponent"), true);
    }

    KederektParasiteTriggeredAbility(final KederektParasiteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KederektParasiteTriggeredAbility copy() {
        return new KederektParasiteTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean youControlRedPermanent = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controllerId)) {
            if (permanent.getColor().isRed()) {
                youControlRedPermanent = true;
            }
        }
        if (event.getType() == GameEvent.EventType.DREW_CARD && game.getOpponents(this.getControllerId()).contains(event.getPlayerId()) &&
                youControlRedPermanent) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws a card, if you control a red permanent, you may have {this} deal 1 damage to that player.";
    }
}

