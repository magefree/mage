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
package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author nantuko
 */
public class GoblinBangchuckers extends CardImpl<GoblinBangchuckers> {

    public GoblinBangchuckers(UUID ownerId) {
        super(ownerId, 137, "Goblin Bangchuckers", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "M12";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Flip a coin. If you win the flip, Goblin Bangchuckers deals 2 damage to target creature or player. If you lose the flip, Goblin Bangchuckers deals 2 damage to itself.
		Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GoblinBangchuckersEffect(), new TapSourceCost());
		ability.addTarget(new TargetCreatureOrPlayer());
		this.addAbility(ability);
    }

    public GoblinBangchuckers(final GoblinBangchuckers card) {
        super(card);
    }

    @Override
    public GoblinBangchuckers copy() {
        return new GoblinBangchuckers(this);
    }
}

class GoblinBangchuckersEffect extends OneShotEffect<GoblinBangchuckersEffect> {

	public GoblinBangchuckersEffect() {
		super(Constants.Outcome.Damage);
		staticText = "{tap}: Flip a coin. If you win the flip, Goblin Bangchuckers deals 2 damage to target creature or player. If you lose the flip, Goblin Bangchuckers deals 2 damage to itself";
	}

	public GoblinBangchuckersEffect(GoblinBangchuckersEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player controller = game.getPlayer(source.getControllerId());
		if (controller != null) {
			if (controller.flipCoin(game)) {
				Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
				if (permanent != null) {
					permanent.damage(2, source.getSourceId(), game, true, false);
					return true;
				}
				Player player = game.getPlayer(targetPointer.getFirst(game, source));
				if (player != null) {
					player.damage(2, source.getSourceId(), game, false, true);
					return true;
				}
			} else {
				Permanent permanent = game.getPermanent(source.getSourceId());
				if (permanent != null) {
					permanent.damage(2, source.getSourceId(), game, true, false);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public GoblinBangchuckersEffect copy() {
		return new GoblinBangchuckersEffect(this);
	}
}
