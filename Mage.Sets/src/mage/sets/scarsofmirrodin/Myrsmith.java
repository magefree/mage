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
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BoostTargetEffect;
import mage.abilities.effects.common.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.MyrToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class Myrsmith extends CardImpl<Myrsmith> {

    public Myrsmith (UUID ownerId) {
        super(ownerId, 16, "Myrsmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Human");
        this.subtype.add("Artificer");
		this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new MyrsmithTriggeredAbility());
    }

    public Myrsmith (final Myrsmith card) {
        super(card);
    }

    @Override
    public Myrsmith copy() {
        return new Myrsmith(this);
    }

}

class MyrsmithTriggeredAbility extends TriggeredAbilityImpl<MyrsmithTriggeredAbility> {
    public MyrsmithTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MyrsmithEffect());

    }

    public MyrsmithTriggeredAbility(final MyrsmithTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MyrsmithTriggeredAbility copy() {
        return new MyrsmithTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getCardType().contains(CardType.ARTIFACT)) {
				return true;
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an artifact spell, you may pay {1}. If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.";
    }
}

class MyrsmithEffect extends OneShotEffect<MyrsmithEffect> {
    public MyrsmithEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
    }

    public MyrsmithEffect(final MyrsmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new GenericManaCost(1);
        cost.clearPaid();
        if (cost.pay(game, source.getId(), source.getControllerId(), false)) {
            new MyrToken().putOntoBattlefield(game, source.getControllerId(), source.getControllerId());
        }
        return true;
    }

    @Override
    public MyrsmithEffect copy() {
        return new MyrsmithEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "You may pay {1}. If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield";
    }
}
