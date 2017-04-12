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

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TectonicEdge extends CardImpl {

    public TectonicEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Tap: Add 1 to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice Tectonic Edge: Destroy target nonbasic land. Activate this ability only if an opponent controls four or more lands.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new DestroyTargetEffect(),
                new ManaCostsImpl("{1}"),
                new OpponentControlsPermanentCondition(
                        new FilterLandPermanent("an opponent controls four or more lands"),
                        ComparisonType.MORE_THAN, 3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
    }

    public TectonicEdge(final TectonicEdge card) {
        super(card);
    }

    @Override
    public TectonicEdge copy() {
        return new TectonicEdge(this);
    }

}

class TectonicEdgeCost extends CostImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    public TectonicEdgeCost() {
        this.text = "Activate this ability only if an opponent controls four or more lands";
    }

    public TectonicEdgeCost(final TectonicEdgeCost cost) {
        super(cost);
    }

    @Override
    public TectonicEdgeCost copy() {
        return new TectonicEdgeCost(this);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        for (UUID opponentId: game.getOpponents(controllerId)) {
            if (game.getBattlefield().countAll(filter, opponentId, game) > 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

}
