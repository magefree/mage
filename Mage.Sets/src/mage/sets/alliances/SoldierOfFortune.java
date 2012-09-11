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
package mage.sets.alliances;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
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
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Backfir3
 */
public class SoldierOfFortune extends CardImpl<SoldierOfFortune> {

    public SoldierOfFortune(UUID ownerId) {
        super(ownerId, 117, "Soldier of Fortune", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ALL";
        this.subtype.add("Human");
        this.subtype.add("Mercenary");
        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
		
		// {R}, {T}: Target player shuffles his or her library.
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoldierOfFortuneEffect(), new TapSourceCost());
		ability.addManaCost(new ManaCostsImpl("{R}"));
		ability.addTarget(new TargetPlayer());
		this.addAbility(ability);
    }

    public SoldierOfFortune(final SoldierOfFortune card) {
        super(card);
    }

    @Override
    public SoldierOfFortune copy() {
        return new SoldierOfFortune(this);
    }
}

class SoldierOfFortuneEffect extends OneShotEffect<SoldierOfFortuneEffect> {

    public SoldierOfFortuneEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles his or her library";
    }

    public SoldierOfFortuneEffect(final SoldierOfFortuneEffect effect) {
        super(effect);
    }

    @Override
    public SoldierOfFortuneEffect copy() {
        return new SoldierOfFortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
			player.shuffleLibrary(game);
            return true;
        }
        return false;
    }

}