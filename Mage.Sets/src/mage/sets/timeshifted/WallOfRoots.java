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
package mage.sets.timeshifted;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerTurnActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.counters.BoostCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class WallOfRoots extends CardImpl<WallOfRoots> {

    public WallOfRoots(UUID ownerId) {
        super(ownerId, 89, "Wall of Roots", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "TSB";
        this.subtype.add("Plant");
        this.subtype.add("Wall");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Put a -0/-1 counter on Wall of Roots: Add {G} to your mana pool. Activate this ability only once each turn.
        Ability ability = new ActivateOncePerTurnActivatedAbility(Constants.Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana), new WallOfRootsCost());
        this.addAbility(ability);
    }

    public WallOfRoots(final WallOfRoots card) {
        super(card);
    }

    @Override
    public WallOfRoots copy() {
        return new WallOfRoots(this);
    }
}

class WallOfRootsCost extends CostImpl<WallOfRootsCost> {

    public WallOfRootsCost() {
        this.text = "Put a -0/-1 counter on {this}";
    }

    public WallOfRootsCost(WallOfRootsCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            permanent.addCounters(new WallOfRootsCounter(), game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public WallOfRootsCost copy() {
        return new WallOfRootsCost(this);
    }
}

class WallOfRootsCounter extends BoostCounter<WallOfRootsCounter> {

    public WallOfRootsCounter() {
        super(0, -1);
    }

    public WallOfRootsCounter(final WallOfRootsCounter counter) {
        super(counter);
    }

    @Override
    public WallOfRootsCounter copy() {
        return new WallOfRootsCounter(this);
    }
}
