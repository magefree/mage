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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.turn.TurnMod;

/**
 *
 * @author emerald000
 */
public class AggravatedAssault extends CardImpl {

    public AggravatedAssault(UUID ownerId) {
        super(ownerId, 185, "Aggravated Assault", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ONS";


        // {3}{R}{R}: Untap all creatures you control. After this main phase, there is an additional combat phase followed by an additional main phase. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "Untap all creatures you control"), new ManaCostsImpl<>("{3}{R}{R}"));
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    public AggravatedAssault(final AggravatedAssault card) {
        super(card);
    }

    @Override
    public AggravatedAssault copy() {
        return new AggravatedAssault(this);
    }
}

class AdditionalCombatPhaseEffect extends OneShotEffect {

    AdditionalCombatPhaseEffect() {
        super(Outcome.Benefit);
        staticText = "After this main phase, there is an additional combat phase followed by an additional main phase";
    }

    AdditionalCombatPhaseEffect(final AdditionalCombatPhaseEffect effect) {
        super(effect);
    }

    @Override
    public AdditionalCombatPhaseEffect copy() {
        return new AdditionalCombatPhaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.POSTCOMBAT_MAIN, null, false));
        game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
        return true;
    }
}