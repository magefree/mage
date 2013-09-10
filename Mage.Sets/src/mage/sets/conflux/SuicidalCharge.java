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
package mage.sets.conflux;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continious.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class SuicidalCharge extends CardImpl<SuicidalCharge> {

    public SuicidalCharge(UUID ownerId) {
        super(ownerId, 128, "Suicidal Charge", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{R}");
        this.expansionSetCode = "CON";

        this.color.setRed(true);
        this.color.setBlack(true);

        // Sacrifice Suicidal Charge: Creatures your opponents control get -1/-1 until end of turn. Those creatures attack this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn), new SacrificeSourceCost());
        ability.addEffect(new SuicidalChargeEffect());
        this.addAbility(ability);
        
    }

    public SuicidalCharge(final SuicidalCharge card) {
        super(card);
    }

    @Override
    public SuicidalCharge copy() {
        return new SuicidalCharge(this);
    }
}

class SuicidalChargeEffect extends RequirementEffect<SuicidalChargeEffect> {

    public SuicidalChargeEffect() {
        super(Duration.EndOfTurn);
        staticText = "Those creatures attack this turn if able";
    }

    public SuicidalChargeEffect(final SuicidalChargeEffect effect) {
        super(effect);
    }

    @Override
    public SuicidalChargeEffect copy() {
        return new SuicidalChargeEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}