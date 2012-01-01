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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class MichikoKondaTruthSeeker extends CardImpl<MichikoKondaTruthSeeker> {

    public MichikoKondaTruthSeeker(UUID ownerId) {
        super(ownerId, 19, "Michiko Konda, Truth Seeker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "SOK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Advisor");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a source an opponent controls deals damage to you, that player sacrifices a permanent.
        this.addAbility(new MichikoKondaTruthSeekerAbility());
    }

    public MichikoKondaTruthSeeker(final MichikoKondaTruthSeeker card) {
        super(card);
    }

    @Override
    public MichikoKondaTruthSeeker copy() {
        return new MichikoKondaTruthSeeker(this);
    }
}

class MichikoKondaTruthSeekerAbility extends TriggeredAbilityImpl<MichikoKondaTruthSeekerAbility> {

    public MichikoKondaTruthSeekerAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 1, "that player"), false);
    }

    public MichikoKondaTruthSeekerAbility(final MichikoKondaTruthSeekerAbility ability) {
        super(ability);
    }

    @Override
    public MichikoKondaTruthSeekerAbility copy() {
        return new MichikoKondaTruthSeekerAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGED_PLAYER)) {
            UUID sourceControllerId = null;
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                sourceControllerId = permanent.getControllerId();
            } else {
                StackObject sourceStackObject = game.getStack().getStackObject(event.getSourceId());
                if (sourceStackObject != null) {
                    sourceControllerId = sourceStackObject.getControllerId();
                }
            }
            if (event.getTargetId().equals(controllerId) && game.getOpponents(controllerId).contains(sourceControllerId)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(sourceControllerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you, that player sacrifices a permanent.";
    }
}
