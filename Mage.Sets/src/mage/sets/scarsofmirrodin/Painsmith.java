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

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class Painsmith extends CardImpl<Painsmith> {

    public Painsmith (UUID ownerId) {
        super(ownerId, 74, "Painsmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Human");
        this.subtype.add("Artificer");
		this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new PainsmithTriggeredAbility());
    }

    public Painsmith (final Painsmith card) {
        super(card);
    }

    @Override
    public Painsmith copy() {
        return new Painsmith(this);
    }
}

class PainsmithTriggeredAbility extends TriggeredAbilityImpl<PainsmithTriggeredAbility> {
    public PainsmithTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn));
        this.addTarget(new TargetCreaturePermanent());
    }

    public PainsmithTriggeredAbility(final PainsmithTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PainsmithTriggeredAbility copy() {
        return new PainsmithTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST  && event.getPlayerId().equals(this.getControllerId())) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getCardType().contains(CardType.ARTIFACT)) {
				return true;
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an artifact spell, you may have target creature get +2/+0 and gain deathtouch until end of turn.";
    }
}
