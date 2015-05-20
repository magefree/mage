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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class CallForBlood extends CardImpl {

    public CallForBlood(UUID ownerId) {
        super(ownerId, 63, "Call for Blood", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{B}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");


        // As an additional cost to cast Call for Blood, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        // Target creature gets -X/-X until end of turn, where X is the sacrificed creature's power.
        DynamicValue xValue = new CallForBloodDynamicValue();
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    public CallForBlood(final CallForBlood card) {
        super(card);
    }

    @Override
    public CallForBlood copy() {
        return new CallForBlood(this);
    }
}

class CallForBloodDynamicValue implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Card sourceCard = game.getCard(sourceAbility.getSourceId());
        if (sourceCard != null) {
            for (Object cost: sourceAbility.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Zone.BATTLEFIELD);
                    if (p != null) {
                        return -1 * p.getPower().getValue();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public CallForBloodDynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return ", where X is the sacrificed creature's power";
    }

    @Override
    public String toString() {
        return "-X";
    }
}