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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;

/**
 *
 * @author emerald000
 */
public class SavageBeating extends CardImpl {

    public SavageBeating(UUID ownerId) {
        super(ownerId, 67, "Savage Beating", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");
        this.expansionSetCode = "DST";

        this.color.setRed(true);

        // Cast Savage Beating only during your turn and only during combat.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SavageBeatingTimingEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        
        // Choose one - Creatures you control gain double strike until end of turn;
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent(), false));
        
        // or untap all creatures you control and after this phase, there is an additional combat phase.
        Mode mode = new Mode();
        mode.getEffects().add(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "untap all creatures you control"));
        mode.getEffects().add(new AdditionalCombatPhaseEffect());
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine {1}{R}
        this.addAbility(new EntwineAbility("{1}{R}"));
    }

    public SavageBeating(final SavageBeating card) {
        super(card);
    }

    @Override
    public SavageBeating copy() {
        return new SavageBeating(this);
    }
}

class SavageBeatingTimingEffect extends ContinuousRuleModifyingEffectImpl {
    SavageBeatingTimingEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only during your turn and only during combat";
    }

    SavageBeatingTimingEffect(final SavageBeatingTimingEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.CAST_SPELL) && event.getSourceId().equals(source.getSourceId())) {
            if (!game.getActivePlayerId().equals(source.getControllerId()) || !TurnPhase.COMBAT.equals(game.getTurn().getPhaseType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SavageBeatingTimingEffect copy() {
        return new SavageBeatingTimingEffect(this);
    }
}

class AdditionalCombatPhaseEffect extends OneShotEffect {

    AdditionalCombatPhaseEffect() {
       super(Outcome.Benefit);
       staticText = "and after this phase, there is an additional combat phase";
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
       game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
       return true;
    }
}