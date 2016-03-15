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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class FlamebladeAngel extends CardImpl {

    public FlamebladeAngel(UUID ownerId) {
        super(ownerId, 157, "Flameblade Angel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Angel");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a source an opponent controls deals damage to you or a permanent you control, you may have Flameblade Angel deal 1 damage to that source's controller.
        this.addAbility(new FlamebladeAngelTriggeredAbility());

    }

    public FlamebladeAngel(final FlamebladeAngel card) {
        super(card);
    }

    @Override
    public FlamebladeAngel copy() {
        return new FlamebladeAngel(this);
    }
}

class FlamebladeAngelTriggeredAbility extends TriggeredAbilityImpl {

    public FlamebladeAngelTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1), true);
    }

    public FlamebladeAngelTriggeredAbility(final FlamebladeAngelTriggeredAbility ability) {
        super(ability);
    }

    @java.lang.Override
    public FlamebladeAngelTriggeredAbility copy() {
        return new FlamebladeAngelTriggeredAbility(this);
    }

    @java.lang.Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event instanceof DamageEvent;
    }

    @java.lang.Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean result = false;
        UUID sourceControllerId = game.getControllerId(event.getSourceId());
        if (sourceControllerId != null && game.getOpponents(getControllerId()).contains(sourceControllerId)) {

            if (event.getTargetId().equals(getControllerId())) {
                result = true;
            } else {
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent != null && getControllerId().equals(permanent.getControllerId())) {
                    result = true;
                }
            }
            if (result) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(sourceControllerId));
                }
            }
        }
        return result;
    }

    @java.lang.Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you or a permanent you control, you may have {this} deal 1 damage to that source's controller.";
    }
}
