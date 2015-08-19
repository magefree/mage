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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class FreneticEfreet extends CardImpl {

    public FreneticEfreet(UUID ownerId) {
        super(ownerId, 324, "Frenetic Efreet", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Efreet");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // {0}: Flip a coin. If you win the flip, Frenetic Efreet phases out. If you lose the flip, sacrifice Frenetic Efreet.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FreneticEfreetEffect(), new GenericManaCost(0)));
    }

    public FreneticEfreet(final FreneticEfreet card) {
        super(card);
    }

    @Override
    public FreneticEfreet copy() {
        return new FreneticEfreet(this);
    }
}

class FreneticEfreetEffect extends OneShotEffect {

    public FreneticEfreetEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, {this} phases out. If you lose the flip, sacrifice {this}";
    }

    public FreneticEfreetEffect(FreneticEfreetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            if (controller.flipCoin(game)) {
                return permanent.phaseOut(game);
            } else {
                permanent.sacrifice(source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public FreneticEfreetEffect copy() {
        return new FreneticEfreetEffect(this);
    }
}
