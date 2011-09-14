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
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class KhalniGem extends CardImpl<KhalniGem> {

	private static final FilterControlledPermanent filter;

	static {
		filter = new FilterControlledPermanent("land you control");
		filter.getCardType().add(CardType.LAND);
	}

    public KhalniGem (UUID ownerId) {
        super(ownerId, 205, "Khalni Gem", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "ZEN";
        
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new KhalniGemReturnToHandTargetEffect());
		Target target = new TargetControlledPermanent(2, 2, filter, false);
		etbAbility.addTarget(target);
		this.addAbility(etbAbility);
		this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(2, 0, 0, 0, 0, 0, 0)), new TapSourceCost()));
		this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(0, 2, 0, 0, 0, 0, 0)), new TapSourceCost()));
		this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(0, 0, 2, 0, 0, 0, 0)), new TapSourceCost()));
		this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(0, 0, 0, 2, 0, 0, 0)), new TapSourceCost()));
		this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BasicManaEffect(new Mana(0, 0, 0, 0, 2, 0, 0)), new TapSourceCost()));
    }

    public KhalniGem (final KhalniGem card) {
        super(card);
    }

    @Override
    public KhalniGem copy() {
        return new KhalniGem(this);
    }

}

class KhalniGemReturnToHandTargetEffect extends OneShotEffect<KhalniGemReturnToHandTargetEffect> {

	private static final String effectText = "return two lands you control to their owner's hand";

	KhalniGemReturnToHandTargetEffect ( ) {
		super(Outcome.ReturnToHand);
		staticText = effectText;
	}

	KhalniGemReturnToHandTargetEffect ( KhalniGemReturnToHandTargetEffect effect ) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for ( UUID target : targetPointer.getTargets(source) ) {
			Permanent permanent = game.getPermanent(target);
			if ( permanent != null ) {
				permanent.moveToZone(Zone.HAND, source.getId(), game, true);
			}
		}
		return true;
	}

	@Override
	public KhalniGemReturnToHandTargetEffect copy() {
		return new KhalniGemReturnToHandTargetEffect(this);
	}

}
