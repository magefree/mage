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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author spjspj
 */
public class DeadlockTrap extends CardImpl {

    public DeadlockTrap(UUID ownerId) {
        super(ownerId, 204, "Deadlock Trap", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "KLD";

        // Deadlock Trap enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Deadlock Trap enters the battlefield, you get {E}{E}
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // {t}, Pay {E}: Tap target creature or planeswalker. Its activated abilities can't be activated this turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new PayEnergyCost(1));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        ability.addEffect(new DeadlockTrapCantActivateEffect());
        this.addAbility(ability);

    }

    public DeadlockTrap(final DeadlockTrap card) {
        super(card);
    }

    @Override
    public DeadlockTrap copy() {
        return new DeadlockTrap(this);
    }
}

class DeadlockTrapCantActivateEffect extends RestrictionEffect {

    public DeadlockTrapCantActivateEffect() {
        super(Duration.EndOfTurn);
        staticText = "Its activated abilities can't be activated this turn";
    }

    public DeadlockTrapCantActivateEffect(final DeadlockTrapCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public DeadlockTrapCantActivateEffect copy() {
        return new DeadlockTrapCantActivateEffect(this);
    }
}
