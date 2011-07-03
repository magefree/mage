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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class BloodshotTrainee extends CardImpl<BloodshotTrainee> {

    public BloodshotTrainee(UUID ownerId) {
        super(ownerId, 85, "Bloodshot Trainee", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new BloodshotTraineeCost());
        this.addAbility(ability);
    }

    public BloodshotTrainee(final BloodshotTrainee card) {
        super(card);
    }

    @Override
    public BloodshotTrainee copy() {
        return new BloodshotTrainee(this);
    }
}

class BloodshotTraineeCost extends CostImpl<BloodshotTraineeCost> {

    public BloodshotTraineeCost() {
        this.text = "Activate this ability only if Bloodshot Trainee's power is 4 or greater";
    }

    public BloodshotTraineeCost(final BloodshotTraineeCost cost) {
        super(cost);
    }

    @Override
    public BloodshotTraineeCost copy() {
        return new BloodshotTraineeCost(this);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            if (permanent.getPower().getValue() >= 4) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
        return paid;
    }
}
