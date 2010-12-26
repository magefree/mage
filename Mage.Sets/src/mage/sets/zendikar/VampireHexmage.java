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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public class VampireHexmage extends CardImpl<VampireHexmage> {

	public VampireHexmage(UUID ownerId) {
		super(ownerId, 114, "Vampire Hexmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{B}{B}");
		this.expansionSetCode = "ZEN";
		this.subtype.add("Vampire");
		this.subtype.add("Shaman");

		this.color.setBlack(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(1);
		
		SimpleActivatedAbility vampireHexmageAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VampireHexmageEffect(), new SacrificeSourceCost());
		vampireHexmageAbility.addTarget(new TargetPermanent());
		this.addAbility(vampireHexmageAbility);
	}

	public VampireHexmage(final VampireHexmage card) {
		super(card);
	}

	@Override
	public VampireHexmage copy() {
		return new VampireHexmage(this);
	}
}

class VampireHexmageEffect extends OneShotEffect<VampireHexmageEffect> {

	VampireHexmageEffect ( ) {
		super(Outcome.Benefit);
	}

	VampireHexmageEffect ( VampireHexmageEffect effect ) {
		super(effect);
	}

	@Override
	public VampireHexmageEffect copy() {
		return new VampireHexmageEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		TargetPermanent target = (TargetPermanent)source.getTargets().get(0);

		Permanent permanent = game.getPermanent(target.getFirstTarget());

		String[ ] counterNames = permanent.getCounters().keySet().toArray(new String[0]);

		if ( permanent.getLoyalty() != null ) {
			permanent.getLoyalty().setValue(0);
		}

		for ( String counterName : counterNames ) {
			permanent.getCounters().remove(counterName);
		}

		return true;
	}

}
