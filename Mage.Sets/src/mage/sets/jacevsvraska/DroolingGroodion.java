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
package mage.sets.jacevsvraska;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DroolingGroodion extends CardImpl<DroolingGroodion> {

    public DroolingGroodion(UUID ownerId) {
        super(ownerId, 65, "Drooling Groodion", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{G}");
        this.expansionSetCode = "DDM";
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {2}{B}{G}, Sacrifice a creature: Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DroolingGroodionEffect(), new ManaCostsImpl("{2}{B}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent(), true)));
        this.addAbility(ability);
    }

    public DroolingGroodion(final DroolingGroodion card) {
        super(card);
    }

    @Override
    public DroolingGroodion copy() {
        return new DroolingGroodion(this);
    }
}

class DroolingGroodionEffect extends ContinuousEffectImpl<DroolingGroodionEffect> {

    public DroolingGroodionEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Target creature gets +2/+2 until end of turn. Another target creature gets -2/-2 until end of turn";
    }

    public DroolingGroodionEffect(final DroolingGroodionEffect effect) {
        super(effect);
    }

    @Override
    public DroolingGroodionEffect copy() {
        return new DroolingGroodionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.addPower(2);
            permanent.addToughness(2);
        }
        permanent = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
        if (permanent != null) {
            permanent.addPower(-2);
            permanent.addToughness(-2);
        }
        return true;
    }
}
