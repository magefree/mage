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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public class KikuNightsFlower extends CardImpl<KikuNightsFlower> {
    
    public KikuNightsFlower (UUID ownerId) {
        super(ownerId, 121, "Kiku, Night's Flower", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Assassin");
	this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // {2}{B}{B}, {T}: Target creature deals damage to itself equal to its power.
        Ability ability;
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new KikuNightsFlowerEffect(), 
                new ManaCostsImpl("{2}{B}{B}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public KikuNightsFlower (final KikuNightsFlower card) {
        super(card);
    }

    @Override
    public KikuNightsFlower copy() {
        return new KikuNightsFlower(this);
    }
}

class KikuNightsFlowerEffect extends OneShotEffect<KikuNightsFlowerEffect> {

        public KikuNightsFlowerEffect() {
            super(Constants.Outcome.Damage);
            this.staticText = "Target creature deals damage to itself equal to its power";        
        }

	public KikuNightsFlowerEffect(final KikuNightsFlowerEffect effect) {
		super(effect);
	}

	@Override
	public KikuNightsFlowerEffect copy() {
		return new KikuNightsFlowerEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(targetPointer.getFirst(source));
		if (permanent != null) {
			permanent.damage(permanent.getPower().getValue(), permanent.getId(), game, true, false);
			return true;
		}
		return false;
	}
}

