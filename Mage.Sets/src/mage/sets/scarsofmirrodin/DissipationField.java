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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetStackObject;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ayratn
 */
public class DissipationField extends CardImpl<DissipationField> {

    public DissipationField (UUID ownerId) {
        super(ownerId, 32, "Dissipation Field", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");
        this.expansionSetCode = "SOM";
       	this.color.setBlue(true);
		this.addAbility(new DissipationFieldEffect());
	}

    public DissipationField (final DissipationField card) {
        super(card);
    }

    @Override
    public DissipationField copy() {
        return new DissipationField(this);
    }

	public class DissipationFieldEffect extends TriggeredAbilityImpl<DissipationFieldEffect> {

		public DissipationFieldEffect() {
			super(Constants.Zone.BATTLEFIELD, new ReturnToHandTargetEffect());
		}

		public DissipationFieldEffect(DissipationFieldEffect effect) {
			super(effect);
		}

		@Override
		public DissipationFieldEffect copy() {
			return new DissipationFieldEffect(this);
		}

		@Override
		public boolean checkTrigger(GameEvent event, Game game) {
			if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.controllerId)) {
				Permanent permanent = game.getPermanent(event.getSourceId());
				if (permanent != null) {
					this.getTargets().clear();
					TargetCreaturePermanent target = new TargetCreaturePermanent();
					target.add(permanent.getId(), game);
					this.addTarget(target);
					return true;
				}
			}
			return false;
		}

		@Override
		public String getRule() {
			return "Whenever a permanent deals damage to you, return it to its owner's hand.";
		}
	}

}
